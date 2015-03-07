package com.jaeger.reading.tools;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jaeger.reading.model.BookInfo;
import com.jaeger.reading.model.SettingsConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonParse {

    public static ArrayList<BookInfo> GetBookInfo(SettingsConfig sc,String jsonString) throws JSONException, IOException {
        ArrayList<BookInfo> booksList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String title = object.getString("title");
            String id = object.getString("id");
            JSONArray authors = object.getJSONArray("author");
            String author = (authors.get(0)).toString();
            int pages = 0;
            String pagesStr = object.getString("pages");
            if (!pagesStr.equals("")) {
                pagesStr = pagesStr.replaceAll("[^\\d]", "");
                pages = Integer.parseInt(pagesStr);
            }
            //get book cover image resource
            JSONObject images = object.getJSONObject("images");
            String imageUrl;
            switch (sc.getLoadCoverQuality()) {
                default:
                case "一般":
                    imageUrl = images.getString("small");
                    break;
                case "中等":
                    imageUrl = images.getString("medium");
                    break;
                case "清晰":
                    imageUrl = images.getString("large");
                    break;
            }
            BookInfo book;
            if (sc.isLoadCoverMode()) {
                Bitmap cover = GetImageFormUrlString(imageUrl);
                book = new BookInfo(id, title, author, pages, imageUrl, cover);
            } else {
                book = new BookInfo(id, title, author, pages, imageUrl);
            }
            booksList.add(book);
        }
        return booksList;
    }

    public static URL getSearchUrl(String keyword, int start, int count) throws MalformedURLException {
        final String SEARCH_URL = "http://api.douban.com/v2/book/search?q=";
        keyword = keyword.replace(" ", "%20");
        String searchUrl = SEARCH_URL + keyword + "&start=" + start + "&count=" + count;
//        Log.d("main", searchUrl);
        return new URL(searchUrl);
    }

    public static Bitmap GetImageFormUrlString(String urlString) throws IOException {
        URL imgUrl = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();
        return bitmap;
    }

    public static String GetJsonString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String jsonString = "";
        int len;
        byte[] data = new byte[1024];
        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.d("main", jsonString);
        return jsonString;

        //另一种方法解析json数据成string
       /*
       InputStreamReader reader = new InputStreamReader(inputStream);
        StringBuffer strB = new StringBuffer();
        String line;
        BufferedReader rd = new BufferedReader(reader);
        while ((line = rd.readLine()) != null) {
            strB.append(line);
        }
        rd.close();
        Log.d("main", strB.toString());
        return strB.toString();
        */
    }

    public static ArrayList<BookInfo> GetBookListByKeyword(SettingsConfig sc,String keyword, int start, int count)
            throws IOException, JSONException {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            url = JsonParse.getSearchUrl(keyword, start, count);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assert url != null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert conn != null;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonParse.GetBookInfo(sc, GetJsonString(is));
    }

}
