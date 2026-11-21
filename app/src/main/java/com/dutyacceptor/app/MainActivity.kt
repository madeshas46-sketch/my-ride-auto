
package com.dutyacceptor.app

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 48, 32, 32)

        val title = TextView(this)
        title.text = "DUTY ACCEPTOR"
        title.textSize = 28f
        layout.addView(title)

        val status = TextView(this)
        status.text = "Ride fare condition: ₹101 and above"
        status.textSize = 18f
        layout.addView(status)

        val button = Button(this)
        button.text = "Enable notification access"
        button.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        layout.addView(button)

        val note = TextView(this)
        note.text = "This version monitors supported ride notifications. It does not reject or automatically accept rides."
        layout.addView(note)

        setContentView(layout)
    }
}
