package com.reddit.client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.reddit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TODO("швидше за все найкращий спосіб пагінації буде з Paging3. потрібно додати +2 вьюхолдера(загрузка і помилка). Також додати swipetorefresh з інвалідейтом DataSource.")
    }
}