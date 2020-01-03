package com.abhijit.rxjava_retrofit_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhijit.rxjava_retrofit_recyclerview.adapter.PostAdapter
import com.abhijit.rxjava_retrofit_recyclerview.data.Post
import com.abhijit.rxjava_retrofit_recyclerview.retrofit.RetrofitObject
import com.abhijit.rxjava_retrofit_recyclerview.retrofit.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var retrofitService: RetrofitService
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitObject.instance
        retrofitService = retrofit.create(RetrofitService::class.java)

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)



        fetchData()
    }

    private fun fetchData() {

        compositeDisposable.add(retrofitService.posts
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                posts -> displayData(posts)
            })
        )

    }

    private fun displayData(posts: List<Post>?) {

        val adapter = PostAdapter(this,posts!!)
        recyclerview.adapter = adapter

    }
}
