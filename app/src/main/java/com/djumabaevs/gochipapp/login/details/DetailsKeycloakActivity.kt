package com.djumabaevs.gochipapp.login.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.MyQuery
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.ActivityDetailsKeycloakBinding
import com.djumabaevs.gochipapp.login.cache.Pet
import com.djumabaevs.gochipapp.login.cache.newAdapter.PetAdapter
import com.djumabaevs.gochipapp.login.cache.newAdapter.PetViewModel
import com.djumabaevs.gochipapp.login.newLogin.apolloClient2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

@AndroidEntryPoint
class DetailsKeycloakActivity  : AppCompatActivity() {

    val viewmodel: PetViewModel by viewModels()

  //  private lateinit var binding: ActivityDetailsKeycloakBinding

    private var job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsKeycloakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PetAdapter()
        binding.detailsRecycler.layoutManager = LinearLayoutManager(this)
        binding.progressBar.visibility = View.VISIBLE
        binding.detailsRecycler.adapter = adapter
        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
//        adapter.onEndOfListReached = {
//            channel.trySend(Unit)
//        }

        viewmodel.pets.observe(this@DetailsKeycloakActivity) { pets ->
//            if (resource != null) {
                pets?.let { pets ->
                    adapter.submitList(pets);
                }
//            }
        }

        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                val response = try {
                    apolloClient2(this@DetailsKeycloakActivity).query(MyQuery()).await()
                } catch (e: ApolloException) {
                    return@launchWhenResumed
                }

                // TODO: Если запрос прошел и есть интернет то смапить в PetData и сохранить в базу данных
                // Если запрос не прошел по причине отсутствия интернета то считать данные с базы и заполнить адаптер

                // TODO: SAVING TO DATABASE

                binding.progressBar.visibility = View.GONE

                val petData = response.data?.pets ?: listOf()

                val list: List<Pet> = petData.map {
                    Pet(

                        it.pet_name ?: "",
                        it.__typename
                    )
                }

                viewmodel.savePets(list)

//                petData?.let {
////                    adapter.submitData(it)
//                }
            }
//            adapter.onEndOfListReached = null
            channel.close()
        }
    }

}