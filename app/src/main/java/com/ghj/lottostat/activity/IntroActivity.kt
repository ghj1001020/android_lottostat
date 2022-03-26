package com.ghj.lottostat.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.ghj.lottostat.LTApp
import com.ghj.lottostat.R
import com.ghj.lottostat.activity.base.BaseActivity
import com.ghj.lottostat.activity.data.LinkData
import com.ghj.lottostat.common.*
import com.ghj.lottostat.databinding.ActivityIntroBinding
import com.ghj.lottostat.db.SQLite
import com.ghj.lottostat.db.SQLiteService
import com.ghj.lottostat.dialog.CommonDialog
import com.ghj.lottostat.util.AlertUtil
import com.ghj.lottostat.util.IntentUtil
import com.ghj.lottostat.util.PrefUtil
import com.ghj.lottostat.util.Util
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.concurrent.timerTask

class IntroActivity : BaseActivity<ActivityIntroBinding>() {

    private val INTRO_TIME = 1 * 1000
    private var mStartTime = Calendar.getInstance().timeInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStartTime = Calendar.getInstance().timeInMillis
    }

    override fun newBinding(): ActivityIntroBinding {
        return ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun onCreateAfter() {
        requestAppVersion()
    }

    // 최신 버전요청
    private fun requestAppVersion() {
        mBinding.txtMessage.text = "앱 버전정보를 가져옵니다"

        val db = FirebaseFirestore.getInstance()
        db.collection("app").document("appinfo").get()
            .addOnSuccessListener { ds: DocumentSnapshot ->
                val version: String = ds["version"] as String
                // 낮은버전이면 업데이트 팝업 노출
                if( !Util.checkAppVersion(version) ) {
                    val dialog = AlertUtil.Alert(this, getString(R.string.dialog_update), getString(R.string.dialog_update_desc))
                    dialog.setPositiveListener { dialog: CommonDialog ->
                        appFinish()
                    }
                    dialog.show()
                    return@addOnSuccessListener
                }
                // 정상버전이면 로또번호 가져오기
                getLottoNumber()
            }
            .addOnFailureListener { exception: Exception ->
                // 버전요청 실패
                val dialog = AlertUtil.Alert(this, getString(R.string.notice), getString(R.string.dialog_version_fail))
                dialog.setPositiveListener { dialog: CommonDialog ->
                    appFinish()
                }
                dialog.show()
            }
    }

    // SQLite에서 데이터 읽기
    private fun getLottoNumber() {
        val copyVersion = PrefUtil.getInstance(this).getInt(DefinePref.VERSION_COPY_SQLITE)
        var isCopy = copyVersion == SQLite.SQLITE_VERSION
        if( !isCopy ) {
            mBinding.txtMessage.text = getString(R.string.intro_guide_copy)

            //  asset폴더에 SQLite파일 확인
            val isExist = Util.checkAssetFileExist(this, "", SQLite.DB_FILE_NAME)

            // SQLite파일 복사
            if( isExist ) {
                val databaseFolderPath = SQLite.databaseFolderPath(this)
                isCopy = Util.copyFileFromAssets(this, SQLite.DB_FILE_NAME, databaseFolderPath, SQLite.DB_FILE_NAME)
                if( isCopy )
                    PrefUtil.getInstance(this).put(DefinePref.VERSION_COPY_SQLITE, SQLite.SQLITE_VERSION)
            }
        }

        if( isCopy ) {
            mBinding.txtMessage.text = getString(R.string.intro_guide_read)

            // SQLite파일 로또 당첨번호 읽기
            LTApp.LottoWinNumberList.clear()
            LTApp.LottoWinNumberList.addAll( SQLiteService.selectLottoWinNumber(this) )
            moveToMain()
        }
        else {
            // 파일복사 실패
            val dialog = AlertUtil.Alert(this, getString(R.string.notice), getString(R.string.dialog_sqlite_fail))
            dialog.setPositiveListener { dialog: CommonDialog ->
                appFinish()
            }
            dialog.show()
        }
    }

    // 메인화면으로 이동
    private fun moveToMain() {
        // 외부링크 확인
        val classCode = intent.getIntExtra(LinkParam.LINK, -1)
        val link = LinkData(classCode)

        val diffTime = Calendar.getInstance().timeInMillis - mStartTime
        // 최소 인트로타임 보장
        if( diffTime < INTRO_TIME ) {
            Timer().schedule( timerTask {
                mBinding.txtMessage.text = getString(R.string.intro_guide_main)
                IntentUtil.moveToMain(this@IntroActivity, link)
                finish()
            }, INTRO_TIME-diffTime)
        }
        else {
            mBinding.txtMessage.text = getString(R.string.intro_guide_main)
            IntentUtil.moveToMain(this, link)
            finish()
        }
    }
}