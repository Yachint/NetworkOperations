package com.blume.networkoperations

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    val githubLists = ArrayList<GithubUser>()
    val ImageList = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = applicationContext
        val looseContext = WeakReference(context)




        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = JsonRecyclerView(githubLists,ImageList)

        btnGrab.setOnClickListener{
            //val v =DownloadTask(looseContext)
            val searchQuery = etUserSearch.text.toString()
            //v.execute("https://api.github.com/search/users?q="+searchQuery)
            makeNetworkCall("https://api.github.com/search/users?q="+searchQuery)
        }
    }

    fun makeNetworkCall(url :String)
    {
        progressBar.visibility = View.VISIBLE
        progressBar.setProgress(0)
        progressBar.max=2000
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call?, e: IOException?) {
                Toast.makeText(this@MainActivity,"Okhttp Failed!",Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(call: Call?, response: Response?) {
                val result = response!!.body()!!.string()
                Log.e("TAG","onResponse: " + result)
                //val users : ArrayList<GithubUser> = parseJson(result!!)

                val gson = Gson()
                val apiResult = gson.fromJson<ApiResult>(result, ApiResult::class.java)


                githubLists.clear()
                githubLists.addAll(apiResult.items)
                //val v = DownloadImageTask(githubLists)
                //v.execute()
                this@MainActivity.runOnUiThread{
                    rvUsers.adapter.notifyDataSetChanged()
                    progressBar.visibility=View.GONE
                }
                Log.d("TAG","onPostExecute: " + githubLists.size)
                //tvInfo.text = result


            }
        })
    }


    /*private inner class DownloadTask(val context: WeakReference<Context>) : AsyncTask<String, Int, String>(){


        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            progressBar.setProgress(0)
            progressBar.max=2000
        }

        override fun doInBackground(vararg params: String?): String {
            val sb = StringBuilder()

            try {
                if(params.isEmpty()) return "NO URL FOUND"

                val webString = params[0]!!
                val url = URL(webString)
                val httpUrl  = url.openConnection() as HttpURLConnection
                val br =  BufferedReader(InputStreamReader(httpUrl.inputStream))
                var buf : String? = ""
                while(buf!=null)
                {
                    sb.append(buf)
                    buf=br.readLine()
                }

            }catch (e : IOException)
            {
                Log.e("ERROR","I/O Exception Occurred!!")

            }

            return sb.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val users : ArrayList<GithubUser> = parseJson(result!!)
            githubLists.clear()
            githubLists.addAll(users)
            rvUsers.adapter.notifyDataSetChanged()
            val v = DownloadImageTask(githubLists)
            v.execute()
            //rvUsers.adapter.notifyDataSetChanged()
            Log.e("TAG","onPostExecute: " + githubLists.size)
            //tvInfo.text = result
        }
    }*/

   /* inner class DownloadImageTask(val githubUserLists: ArrayList<GithubUser>) : AsyncTask<String, Int, ArrayList<Bitmap>>()
    {
        val internalImageList = ArrayList<Bitmap>()
        override fun doInBackground(vararg params: String?): ArrayList<Bitmap> {
            var mIcon11: Bitmap? = null

            try {
                val size = githubUserLists.size-1
                for(i in 0..size)
                {
                    Log.e("TAG","doInBackground: " + githubLists.size)

                    val interval = size/3

                    Log.e("TAG","Interval " + interval)

                    val webString = githubUserLists[i].avatar_url
                    val inp : InputStream = java.net.URL(webString).openStream()
                    mIcon11 = BitmapFactory.decodeStream(inp)
                    internalImageList.add(mIcon11)
                    ImageList.clear()
                    ImageList.addAll(internalImageList)
                    publishProgress(i)

                    /*if(i%interval==0 && i!=0 && interval!=0)
                    {
                        Log.d("TAG","Pass value :"+i)
                        publishProgress(i)
                    }*/
                    /*when(i)
                    {
                        10->publishProgress(i)
                        20->publishProgress(i)
                    }*/
                }

            }catch (e : IOException)
            {
                Log.e("ERROR","I/O Exception Occurred!!")

            }
            catch (e :ArithmeticException)
            {
                Log.e("ERROR","Arithemetic Exception Occurred!!")
            }

            return internalImageList
        }

        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            Log.d("TAG", "Value on notify :"+values[0]!!)
            rvUsers.adapter.notifyDataSetChanged()
        }

        override fun onPostExecute(result: ArrayList<Bitmap>) {
            super.onPostExecute(result)
            ImageList.clear()
            ImageList.addAll(result)
            progressBar.visibility=View.GONE
            rvUsers.adapter.notifyDataSetChanged()
        }

    }*/

    /*fun parseJson(s :String) : ArrayList<GithubUser>
    {
        val githubUser = ArrayList<GithubUser>()

        //Parse JSON
        try {
            val root = JSONObject(s)

            val items = root.getJSONArray("items")

            for(i in 0..items.length())
            {
                val obj = items.getJSONObject(i)
                val login  = obj.getString("login")
                val id = obj.getInt("id")
                val avatar = obj.getString("avatar_url")
                val score = obj.getDouble("score")
                val html = obj.getString("html_url")


                val newGithubUser = GithubUser(login,id,html,score,avatar)

                githubUser.add(newGithubUser)

            }


        }catch (e : JSONException)
        {

        }


        return githubUser
    }*/
}