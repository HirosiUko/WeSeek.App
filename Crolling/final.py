import pandas as pd
import requests as req
import sqlite3 as db
import json
from selenium import webdriver as wb
from selenium.webdriver.common.by import By
import time
from tqdm import tqdm

MAIN_DB = './wesharingDB.db'


def store_df_to_db(df, tableName):
    conn = db.connect(MAIN_DB)
    df.to_sql(tableName, conn, if_exists="replace")
    conn.close()


def read_DB(dbfile):
    conn = db.connect(dbfile)
    fda_df = pd.read_sql_query('select * from FDA', conn)
    conn.close()
    fda_df = fda_df[['지정번호', '업소명', '별점', '소재지', '지정기관구분', 'GPS']]

    # GPS 필드를 x,y로 나눔.
    gpsx = []
    gpsy = []
    for item in tqdm(fda_df['GPS'], desc='Modify GPS info to GPSX, GPSY..', total=len(fda_df)):
        x, y = item.split(',')
        gpsx.append(x)
        gpsy.append(y)
    fda_df['GPSX'] = pd.Series(gpsx)
    fda_df['GPSY'] = pd.Series(gpsy)
    del fda_df['GPS']

    new_fda_list = []
    for lat, long, name, hid, star, evl_fda \
            in tqdm(zip(fda_df['GPSX'], fda_df['GPSY'], fda_df['업소명'], fda_df['지정번호'], fda_df['별점'], fda_df['지정기관구분']),
                    desc='GET Data from Kakao MAP API', total=len(fda_df)):
        tmp_dic = get_store_info(lat, long, name)
        if len(tmp_dic) == 0:
            continue
        tmp_dic['evl_num'] = hid
        tmp_dic['evl_grade'] = star
        tmp_dic['evl_fda'] = evl_fda
        new_fda_list.append(tmp_dic)

    new_fda_df = pd.read_json(json.dumps(new_fda_list, ensure_ascii=False))

    return new_fda_df


def get_store_info(latitude, longitude, query_name):
    header = {'Authorization': 'KakaoAK 647742ba852c5d05653d9d6ca4666b70'}

    url = "https://dapi.kakao.com/v2/local/search/keyword.json"
    res = req.get(url, headers=header, params={'page': 1,
                                               'size': 15,
                                               'sort': 'accuracy',
                                               'radius': 10000,
                                               'y': latitude,
                                               'x': longitude,
                                               'query': query_name,
                                               })
    raw_json = json.loads(res.text)['documents']
    if len(raw_json) < 1:
        return {}
    store_Dic = raw_json[0]
    return store_Dic


def make_store_table_df(fda_df):
    store_info_detail_dicts = []
    for store_id, gpsx, gpsy, evl_num, evl_grade, evl_fda \
            in tqdm(zip(fda_df['id'], fda_df['x'], fda_df['y'], fda_df['evl_num'], fda_df['evl_grade'], fda_df['evl_fda']),
                    desc='crolling store detail information from Kakao MAP place', total=len(fda_df)):
        store_info_detail_dict = get_store_info_detail(store_id)
        store_info_detail_dict['store_lat'] = gpsx
        store_info_detail_dict['store_long'] = gpsy
        store_info_detail_dict['evl_num'] = evl_num
        store_info_detail_dict['evl_grade'] = evl_grade
        store_info_detail_dict['evl_fda'] = evl_fda
        store_info_detail_dicts.append(store_info_detail_dict)
    store_info_detail_df = pd.read_json(json.dumps(
        store_info_detail_dicts, ensure_ascii=False))
    store_df_to_db(store_info_detail_df, 'tbl_store')


def get_menu(driver):
    try:
        driver.find_element(
            By.CSS_SELECTOR, value='#mArticle > div.cont_menu > a > span.open_txt').click()
    except:
        pass
    menu_all = []
    try:
        menus = driver.find_element(By.CLASS_NAME, value='list_menu')

        menutitles = menus.find_elements(By.CLASS_NAME, value='info_menu')
        #menuprices = menus.find_elements(By.CLASS_NAME, value='price_menu')
        for menu in menutitles:
            menu_all.append(menu.text.replace('\n', ':'))
    except:
        pass
    return menu_all


def get_pic(driver):
    image_urls = []
    pic_list = driver.find_elements(By.CLASS_NAME, value='link_photo')
    for ele in pic_list:
        raw_url = ele.get_attribute('style')
        raw_url = raw_url[raw_url.find('http'):raw_url.rfind('");')]
        raw_url = raw_url.replace('%2F', '/')
        raw_url = raw_url.replace('%3A', ':')
        raw_url = raw_url.replace('%3F', '?')
        image_urls.append(raw_url)
        # print(raw_url)
    return image_urls


def get_store_info_detail(store_id):
    driver = wb.Chrome('./chromedriver.exe')
    map_url = 'https://place.map.kakao.com/'+f'{store_id}'
    driver.get(map_url)
    time.sleep(2)

    try:
        storeNm = driver.find_element(
            By.CSS_SELECTOR, value='#mArticle > div.cont_essential > div:nth-child(1) > div.place_details > div > h2').text
    except:
        storeNm = "N/A"
    try:
        storeAddr = driver.find_element(
            By.CSS_SELECTOR, value='#mArticle > div.cont_essential > div.details_placeinfo > div:nth-child(2) > div > span.txt_address').text
    except:
        storeAddr = "N/A"
    try:
        storeTime = driver.find_element(
            By.CSS_SELECTOR, value='#mArticle > div.cont_essential > div.details_placeinfo > div:nth-child(3) > div > div > ul > li:nth-child(1) > span > span').text
    except:
        storeTime = "N/A"
    try:
        storePhone = driver.find_element(
            By.CSS_SELECTOR, value='#mArticle > div.cont_essential > div.details_placeinfo > div.placeinfo_default.placeinfo_contact > div > div > span > span.txt_contact').text
    except:
        storePhone = "N/A"
    storeMenus = get_menu(driver)
    storePics = get_pic(driver)

    store_detail_info = {
        "store_id": store_id,
        "store_name": storeNm,
        "store_tel": storePhone,
        "store_addr": storeAddr,
        "store_hours": storeTime,
        "store_img": ','.join(storePics),
        "store_menu": ','.join(storeMenus),
    }
    return store_detail_info


def main():
    fda_raw_db_file = './test.db'
    fda_df = read_DB(fda_raw_db_file)
    store_df_to_db(fda_df, 'FDA')


def main2():
    conn = db.connect(MAIN_DB)
    fda_df = pd.read_sql_query('select * from FDA', conn)
    conn.close()
    make_store_table_df(fda_df)


if __name__ == '__main__':
    main()
    main2()
