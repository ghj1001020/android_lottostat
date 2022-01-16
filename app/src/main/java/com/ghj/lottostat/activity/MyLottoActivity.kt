package com.ghj.lottostat.activity

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.adapter.MyLottoAdapter
import com.ghj.lottostat.activity.base.BaseDrawerViewModelActivity
import com.ghj.lottostat.activity.base.IListListener
import com.ghj.lottostat.activity.data.MyLottoType
import com.ghj.lottostat.activity.viewmodel.MyLottoViewModel
import com.ghj.lottostat.databinding.ActivityMyLottoBinding
import com.ghj.lottostat.db.SQLiteService

class MyLottoActivity : BaseDrawerViewModelActivity<ActivityMyLottoBinding, MyLottoViewModel>() {

    lateinit var myLottoAdapter : MyLottoAdapter

    override fun newViewModel(): MyLottoViewModel {
        return ViewModelProvider(this).get(MyLottoViewModel::class.java)
    }

    override fun newContentBinding(): ActivityMyLottoBinding {
        return ActivityMyLottoBinding.inflate(layoutInflater)
    }


    override fun onCreateAfter() {
        getViewModel().mOrgMyLottoList = SQLiteService.selectMyLottoRoundNo(this)
        getViewModel().calculateList()
        initLayout()
    }

    fun initLayout() {
        mContent.noContent.txtDesc.text = getString(R.string.no_number_mylotto)
        myLottoAdapter = MyLottoAdapter(this, getViewModel().mMyLottoList)
        myLottoAdapter.mListener = object : IListListener {
            // 열기접기
            override fun onItemClick(position: Int) {
                getViewModel().mMyLottoList[position].toggleType()
                if( !getViewModel().isDataExist(position) ) {
                    val no = getViewModel().mMyLottoList[position].roundNo
                    val list = SQLiteService.selectMyLottoData(this@MyLottoActivity, no)
                    getViewModel().mOrgMyLottoList.addAll(position+1, list)
                }
                getViewModel().calculateList()
                myLottoAdapter.notifyDataSetChanged()
            }
        }
        mContent.rvMyLotto.adapter = myLottoAdapter
        renderMyLottoList()
    }

    fun renderMyLottoList() {
        if( getViewModel().mMyLottoList.size > 0 ) {
            mContent.rvMyLotto.visibility = View.VISIBLE
            mContent.noContent.root.visibility = View.GONE
        }
        else {
            mContent.rvMyLotto.visibility = View.GONE
            mContent.noContent.root.visibility = View.VISIBLE
        }
    }
}