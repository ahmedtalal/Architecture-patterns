package ahmed.javcoder.egynews.Adapters;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import ahmed.javcoder.egynews.Models.ItemModel;

public class ConnectionsAdapter
{

    public static String title, imageurl , sourceUrl  , nameNew , description , publishedAt;
    private static final String LOG_TAG =  ConnectionsAdapter.class.getSimpleName() ;

    public static List<ItemModel> FetchDataFromUrl(String receiveUrl)
    {
        URL url = CreateUrl(receiveUrl) ;
        String read  = null ;

        try {
            read = MakeHttpRquest(url) ;
        } catch (IOException e)
        {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<ItemModel>  book = ExtractFromJson(read) ;

        return  book ;
    }

    private static URL CreateUrl(String UrlSource)
    {
        URL url = null ;

        try {
            url =  new URL(UrlSource) ;
        } catch (MalformedURLException e)
        {
            Log.e(LOG_TAG ,"ERROR IN URL Creation" , e);
        }

        return url ;
    }

    private static  String MakeHttpRquest(URL url) throws IOException {
        String jsonresponse = "" ;

        if(url == null)
        {
            return jsonresponse ;
        }

        HttpURLConnection httpURLConnection = null ;
        InputStream inputStream = null ;

        try
        {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200)
            {
                inputStream = httpURLConnection.getInputStream() ;
                jsonresponse = ReadFromStream(inputStream) ;

            }else
                {
                    Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
                }

        }catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if(httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }

            if(inputStream != null)
            {
                inputStream.close();
            }
        }

        return jsonresponse ;
    }

    private static String ReadFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder() ;

        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream , Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader) ;
            String line = bufferedReader.readLine() ;

            while(line != null)
            {
                stringBuilder.append(line) ;
                line = bufferedReader.readLine() ;

            }
        }

        return stringBuilder.toString() ;
    }

    private static List<ItemModel> ExtractFromJson(String BookJson)
    {
        if(TextUtils.isEmpty(BookJson))
        {
            return  null ;
        }

        List<ItemModel> book =  new ArrayList<>() ;

        try
        {
            JSONObject jsonObject = new JSONObject(BookJson) ;
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for(int i = 0 ; i<jsonArray.length() ; i++)
            {
                JSONObject FirstItem = jsonArray.getJSONObject(i) ;

                if(FirstItem.has("title"))
                {
                    title = FirstItem.getString("title");
                }else
                {
                    title = "Title not found" ;
                }

                if(FirstItem.has("urlToImage"))
                {
                    imageurl = FirstItem.getString("urlToImage") ;
                }else
                {
                    imageurl = "Image not found" ;
                }

                if(FirstItem.has("url"))
                {
                    sourceUrl =FirstItem.getString("url") ;
                }else
                {
                    sourceUrl = "Url not found" ;
                }

                if(FirstItem.has("source"))
                {
                    JSONObject result = (JSONObject) FirstItem.get("source");
                    if (result.has("name")){
                        nameNew = (String) result.get("name");
                    }else {
                        nameNew = "name not found" ;
                    }

                }

                if (FirstItem.has("description")){
                    description = FirstItem.getString("description");
                }else {
                    description = "description not found" ;
                }

                if (FirstItem.has("publishedAt")){
                    publishedAt = FirstItem.getString("publishedAt") ;
                }else {
                    publishedAt = "publishedAt not found" ;
                }
                book.add(new ItemModel(title , imageurl  , sourceUrl , nameNew , description , publishedAt)) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return book ;
    }
}