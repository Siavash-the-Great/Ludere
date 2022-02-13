package com.draco.ludere.views
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.net.ConnectivityManager
import java.net.URL
import java.net.URLConnection
import android.content.ContentValues.TAG
import android.app.ProgressDialog
import android.util.Log
import android.view.ViewGroup
import android.os.AsyncTask
import android.content.Context
import android.widget.Toast  
import android.graphics.Paint
import android.widget.TextView
import android.widget.ProgressBar
import java.io.*
import java.util.zip.ZipFile
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.FileOutputStream
import android.widget.RelativeLayout
import java.util.*
import kotlin.system.exitProcess
import android.net.Uri
import android.content.Intent
import android.app.Activity
import android.app.Service
import android.content.DialogInterface
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.draco.ludere.R
import com.draco.ludere.gamepad.GamePad
import com.draco.ludere.gamepad.GamePadConfig
//import com.draco.ludere.input.ControllerInput
//import com.draco.ludere.retroview.RetroView
import com.draco.ludere.utils.RetroViewUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import android.graphics.Color
import android.content.pm.ActivityInfo
import ir.tapsell.plus.TapsellPlus;
import ir.tapsell.plus.TapsellPlusInitListener
import ir.tapsell.plus.model.AdNetworkError
import ir.tapsell.plus.model.AdNetworks
import android.net.NetworkCapabilities

class MainActivity : AppCompatActivity() {

    
     val BUFFER_SIZE = 4096 * 8
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
	
		
	        TapsellPlus.initialize(this@MainActivity, "rnlgolshmnbapnbqkdliqmjrlqrdgdolbohjmireeackhchipkchbimoalcenjejpbpnae" ,
				       object : TapsellPlusInitListener {
       
            override fun onInitializeSuccess(adNetworks : AdNetworks) {
          //      Log.d("onInitializeSuccess", adNetworks.name())
            }
					       override fun onInitializeFailed( adNetworks : AdNetworks,
						 adNetworkError : AdNetworkError) {
             //   Log.e("onInitializeFailed", "ad network: " + adNetworks.name() + ", error: " +	adNetworkError.getErrorMessage())
            }
        })
	
	        val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
	   
        val file = File(storagePath + "Records.txt")
        var fileExists = file.exists()
         if(fileExists){
         //nothing
	 }else{
                 file.writeText("0")
	 }//init the first time invertisement randomly
  
  
    }


    /** Called when the user touches the button */
    fun start(view: View) {
	    
	   val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
        val cfile = File(storagePath + "/example.sfc")//diffrent for each game
        var fileExists = cfile.exists()
    val bfile = File(storagePath + "/game.zip")
        var fileExistscheck = bfile.exists()

            
    if(fileExists){
            if(fileExistscheck){
              bfile.delete()
	    }
	
	   if (isOnline(this@MainActivity)){     
           startActivity(Intent(this@MainActivity, InterstitialActivity::class.java))
	   }
	   else{
           startActivity(Intent(this@MainActivity, GameActivity::class.java))	   
	   }
    
    } else {

	    
        // Do something in response to button click
        val start_the_game_button = findViewById(R.id.start_the_game_button) as Button
        start_the_game_button.isEnabled = false
        start_the_game_button.visibility = View.INVISIBLE
        val comments = findViewById(R.id.comments) as Button
        comments.isEnabled = false
        comments.visibility = View.GONE
        val game_page = findViewById(R.id.game_page) as Button
        game_page.isEnabled = false
        game_page.visibility = View.GONE
        val exit_button = findViewById(R.id.exit_button) as Button
        exit_button.isEnabled = false
        exit_button.visibility = View.GONE
        val send_email = findViewById(R.id.send_email) as Button
        send_email.isEnabled = false
        send_email.visibility = View.GONE
	val group_games_page = findViewById(R.id.group_games_page) as Button
        group_games_page.isEnabled = false
        group_games_page.visibility = View.GONE    
	val start_the_hub = findViewById(R.id.start_the_hub) as Button
        start_the_hub.isEnabled = false
        start_the_hub.visibility = View.GONE      
        val relative = findViewById(R.id.relative) as RelativeLayout
        relative.setBackgroundResource(0)
        relative.setBackgroundColor(Color.parseColor("#000000"))
			

				        someTask(this,this).execute()
		
        }
    }

    fun sendMsg(view: View) {
	
	/*myket*/
	val openURL = Intent(android.content.Intent.ACTION_VIEW)	    
        openURL.data = Uri.parse("myket://comment?id=com.draco.ludere.superpunch")
        
	/*bazar*/
	//val openURL = Intent(android.content.Intent.ACTION_EDIT)
        //openURL.data = Uri.parse("bazaar://details?id=com.draco.ludere.superpunch")
        //openURL.setPackage("com.farsitel.bazaar")
	
	
        startActivity(openURL)
    }

    fun sendingEmail(view: View) {

        val intent = Intent(Intent.ACTION_SENDTO)
        
	    
	/*myket*/
	intent.data = Uri.parse("mailto: siavashiranpak@gmail.com")
        /*bazar*/
        //intent.data = Uri.parse("mailto: 00sohrabiranpak00@gmail.com")        
	
	    
	    
	intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی بوکس تایسون")
        startActivity(intent)

    }

    fun goToPage(view: View) {
        
	//for both
	val openURL = Intent(android.content.Intent.ACTION_VIEW)
	
	
	/*myket*/
        openURL.data = Uri.parse("myket://details?id=com.draco.ludere.superpunch")
	
	/*bazar*/
        //openURL.data = Uri.parse("bazaar://details?id=com.draco.ludere.superpunch")
        //openURL.setPackage("com.farsitel.bazaar")
	    
	    
	startActivity(openURL)
    }
  
  fun goToGamesPage(view: View) {
	/*myket*/
	val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://developer/com.draco.ludere.superpunch")
	startActivity(openURL)
	
        /*bazar*/
	/*
	val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("bazaar://collection?slug=by_author&aid=" + "230310009713")
        openURL.setPackage("com.farsitel.bazaar")
	startActivity(openURL)
	*/
	
    }
    
            fun ourHub(view: View) {
	val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("http://sandsbros.ctcin.bio")
	startActivity(openURL)
    }
    
    fun exit_game(view: View) {
        this@MainActivity.finish()
        exitProcess(0)
    }

        
        /*asynctask start
        */
        class someTask( context:Context , mainActivity: MainActivity ) : AsyncTask<Void, String, String>() {
    
            var context: Context = context 
            val roootView = mainActivity
             val BUFFER_SIZE = 4096 * 8
	
             val TAG = "MyMessage"
            var current : Double = 0.0
            var current_copy : Double = 0.0
            var prev : Double = -1.0
		var prev_copy : Double = -1.0
	
	var prev_download : Double = -1.0
		val storagePath: String = (context.getExternalFilesDir(null) ?: context.filesDir).path             
			var ll = 7816696 
		        var ll_zip = 7816696
	            	

            var toshoow = 0
		val zipFilePath = File(storagePath + "/game.zip")
            val destDirectory = storagePath 
		
	        val myProgressDialog = ProgressDialog(context)
//for copy
    val afile = context.assets.open( "game.zip" )
    val bfile = File(storagePath + "/game.zip")	
            
     override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context,"برای اجرای اولیه نیاز به آماده سازی وجود دارد",Toast.LENGTH_LONG).show()  
	       	myProgressDialog.setMessage("در حال انجام عملیات...لطفا شکیبا باشید")
		myProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
		myProgressDialog.setCancelable(false)
		myProgressDialog.setMax(100)
	        myProgressDialog.show()
		

    }
            
            
            
            override fun doInBackground(vararg params: Void):String? {
		    
		    //copy1
    var inStream: InputStream? = null
    var outStream: OutputStream? = null
    inStream = afile
    outStream = FileOutputStream(bfile)
    val buffer = ByteArray(1024*10)
    var length = inStream.read(buffer)
    while (length    > 0 )
    {
	    current_copy += length.toDouble()
	    		if(prev_copy != current_copy / ll * 5) {
                           prev_copy = current_copy / ll * 5
                           toshoow = prev_copy.toInt()    
			   publishProgress(""+toshoow)
                           }   
        outStream.write(buffer, 0, length)
        length = inStream.read(buffer)
    }
    inStream.close()
    outStream.close()
    //copy1
    
        //unzip            
        val destDir = File(destDirectory)
	//val destDir = fileWithinMyDir
        if (!destDir.exists()) {
            destDir.mkdir()
        }
        ZipFile(zipFilePath).use { zip ->

            zip.entries().asSequence().forEach { entry ->

                zip.getInputStream(entry).use { input ->


                        val filePath = destDirectory + File.separator + entry.name
                                            
                        if (!entry.isDirectory) {
                            // if the entry is a file, extracts it
                            val bos = BufferedOutputStream(FileOutputStream(filePath))
                            val bytesIn = ByteArray(BUFFER_SIZE)
                            var read: Int
                           while (input.read(bytesIn).also { read = it } != -1) {
			   current += read.toDouble()
			   if(prev != current / ll_zip * 5) {
                           prev = current / ll_zip * 5
                           toshoow = prev_copy.toInt() + prev.toInt()     
			   publishProgress(""+toshoow)
                           }   
                           bos.write(bytesIn, 0, read)
                           }
                           bos.close()
                            /*new
                            */

                            
                        } else {
                            // if the entry is a directory, make the directory
                            val dir = File(filePath)
                            dir.mkdir()
                        }

                }

            }
        }
	//unzip  
		    
		 
 return "finished"
            }
            
            
      override fun onProgressUpdate(vararg values: String) {
          super.onProgressUpdate(values.toString())
          myProgressDialog.setProgress(Integer.parseInt(values[0]))    
      }
    
    override fun onPostExecute(values: String) {
	super.onPostExecute(values)
   		if(myProgressDialog != null && myProgressDialog.isShowing()){
			myProgressDialog.dismiss()
		}
	    
    val dfile = File(storagePath + "/game.zip")	
            var fileExistscheck3 = dfile.exists()
            if(fileExistscheck3){
              dfile.delete()
              }

               Toast.makeText(context,"عملیات تکمیل شد...از صبر شما متشکریم",Toast.LENGTH_LONG).show()  
	   
	   var connnneccct : Boolean = false
	   
	       val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
		connnneccct = true
            val intent = Intent(context, InterstitialActivity::class.java)
            context.startActivity(intent)
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
		connnneccct = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
		connnneccct = true

            }
        }
    }
    if(connnneccct == true){
            val intent = Intent(context, InterstitialActivity::class.java)
            context.startActivity(intent)
    }else{	    
    	    val intent = Intent(context, GameActivity::class.java)
            context.startActivity(intent)
    } 
	   
    }
}
/*asynctask end
*/        
        
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}        
        

}
