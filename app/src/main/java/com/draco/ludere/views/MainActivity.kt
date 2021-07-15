package com.draco.ludere.views
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
import com.draco.ludere.input.ControllerInput
import com.draco.ludere.retroview.RetroView
import com.draco.ludere.utils.RetroViewUtils
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    /** Called when the user touches the button */
    fun start(view: View) {
        // Do something in response to button click
        val start_the_game_button = findViewById(R.id.start_the_game_button) as Button
        start_the_game_button.isEnabled = false
        start_the_game_button.visibility = View.INVISIBLE
        val comments = findViewById(R.id.comments) as Button
        comments.isEnabled = false
        comments.visibility = View.INVISIBLE
        val game_page = findViewById(R.id.game_page) as Button
        game_page.isEnabled = false
        game_page.visibility = View.INVISIBLE
        val exit_button = findViewById(R.id.exit_button) as Button
        exit_button.isEnabled = false
        exit_button.visibility = View.INVISIBLE
        val send_email = findViewById(R.id.send_email) as Button
        send_email.isEnabled = false
        send_email.visibility = View.INVISIBLE
        val relative = findViewById(R.id.relative) as RelativeLayout
        relative.setBackgroundResource(0)
        
        /*
        new-> just copying
        */
        val storagePath: String = (this.getExternalFilesDir(null) ?: this.filesDir).path
        InputStream in = getResources().openRawResource(R.raw.example)
FileOutputStream out = new FileOutputStream(storagePath)
byte[] buff = new byte[1024]
int read = 0

try {
   while ((read = in.read(buff)) > 0) {
      out.write(buff, 0, read)
   }
} finally {
     in.close()
     out.close()
}
        /*
        new-> just copying
        */
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun sendMsg(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://comment?id=com.draco.ludere.MetroidZerFusion")
        startActivity(openURL)
    }

    fun sendingEmail(view: View) {

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: siavashiranpak@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "نظر دهی")
        startActivity(intent)

    }

    fun goToPage(view: View) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("myket://details?id=com.draco.ludere.MetroidZerFusion")
        startActivity(openURL)
    }

    fun exit_game(view: View) {
        this@MainActivity.finish()
        exitProcess(0)
    }
    
    
public void unzipFile(String targetPath, String zipFilePath) {  
  
      try {  
          File zipFile = new File(zipFilePath);  
          InputStream is = new FileInputStream(zipFile);  
          ZipInputStream zis = new ZipInputStream(is);  
          ZipEntry entry = null;  
          System.out.println("Start decompression:" + zipFile.getName() + "...");  
          while ((entry = zis.getNextEntry()) != null) {  
              String zipPath = entry.getName();  
              try {  
  
                  if (entry.isDirectory()) {  
                      File zipFolder = new File(targetPath + File.separator  
                              + zipPath);  
                      if (!zipFolder.exists()) {  
                          zipFolder.mkdirs();  
                      }  
                  } else {  
                      File file = new File(targetPath + File.separator  
                              + zipPath);  
                      if (!file.exists()) {  
                          File pathDir = file.getParentFile();  
                          pathDir.mkdirs();  
                          file.createNewFile();  
                      }  
  
                      FileOutputStream fos = new FileOutputStream(file);  
                      int bread;  
                      while ((bread = zis.read()) != -1) {  
                          fos.write(bread);  
                      }  
                      fos.close();  
  
                  }  
                                     System.out.println("Successfully decompressed:" + zipPath);  
  
              } catch (Exception e) {  
                                     System.out.println("Unzip" + zipPath + "Failed");  
                  continue;  
              }  
          }  
          zis.close();  
          is.close();  
                     System.out.println("decompression end");  
      } catch (Exception e) {  
          e.printStackTrace();  
      }  
  
  }  

}
