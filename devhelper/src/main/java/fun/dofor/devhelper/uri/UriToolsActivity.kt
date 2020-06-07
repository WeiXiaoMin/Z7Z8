package `fun`.dofor.devhelper.uri

import `fun`.dofor.devhelper.R
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class UriToolsActivity : AppCompatActivity() {

    private lateinit var input: AppCompatEditText
    private lateinit var btnParse: AppCompatButton
    private lateinit var btnEncode: AppCompatButton
    private lateinit var tvDisplay: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uritools)

        input = findViewById<AppCompatEditText>(R.id.input)
        btnParse = findViewById<AppCompatButton>(R.id.btnParse)
        btnEncode = findViewById<AppCompatButton>(R.id.btnEncode)
        tvDisplay = findViewById<AppCompatTextView>(R.id.tvDisplay)

        btnParse.setOnClickListener { parseUri() }
        btnEncode.setOnClickListener { encodeUri() }
    }

    fun parseUri() {
        val text = input.text.toString()
        val uri = Uri.parse(text)
        val lines = mutableListOf<String>()
        lines.add("url: ${uri.scheme}://${uri.host}${uri.path}")
        lines.add("query字段：")
        uri.queryParameterNames.forEach {
            lines.add("$it: ${uri.getQueryParameter(it)}")
        }
        tvDisplay.text = lines.joinToString("\n\n")
    }

    fun encodeUri() {
        val text = input.text.toString()
        val encodedUri = Uri.encode(text)
        tvDisplay.text = encodedUri
    }
}