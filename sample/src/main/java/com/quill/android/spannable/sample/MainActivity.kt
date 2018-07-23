package com.quill.android.spannable.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.quill.android.delta.Delta
import com.quill.android.spannable.ConverterDeltaToSpanned
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sampleJson = "{\"ops\":[{\"insert\":\"normal \"},{\"attributes\":{\"bold\":true},\"insert\":\"bold\"},{\"insert\":\" \"},{\"attributes\":{\"italic\":true},\"insert\":\"italicun\"},{\"attributes\":{\"underline\":true,\"italic\":true},\"insert\":\"derline\"},{\"attributes\":{\"underline\":true,\"strike\":true,\"italic\":true},\"insert\":\"strike  \"},{\"attributes\":{\"underline\":true,\"strike\":true,\"italic\":true,\"script\":\"super\"},\"insert\":\"super\"},{\"attributes\":{\"underline\":true,\"strike\":true,\"italic\":true,\"script\":\"sub\"},\"insert\":\"sub \"},{\"attributes\":{\"italic\":true,\"color\":\"#e60000\",\"bold\":true},\"insert\":\"color\"},{\"attributes\":{\"strike\":true,\"italic\":true,\"color\":\"#e60000\",\"bold\":true},\"insert\":\" \"},{\"attributes\":{\"underline\":true,\"strike\":true,\"color\":\"#f06666\",\"background\":\"#a10000\",\"italic\":true},\"insert\":\"background\"},{\"insert\":\"\\n\"},{\"attributes\":{\"underline\":true,\"strike\":true,\"italic\":true},\"insert\":\"header1\"},{\"attributes\":{\"header\":1},\"insert\":\"\\n\"},{\"insert\":\"header2\"},{\"attributes\":{\"header\":2},\"insert\":\"\\n\"},{\"insert\":\"header 3\"},{\"attributes\":{\"header\":3},\"insert\":\"\\n\"},{\"insert\":\"q\"},{\"attributes\":{\"bold\":true},\"insert\":\"uo\"},{\"insert\":\"te\"},{\"attributes\":{\"blockquote\":true},\"insert\":\"\\n\"},{\"insert\":\"code\"},{\"attributes\":{\"code-block\":true},\"insert\":\"\\n\"},{\"insert\":\"indent\"},{\"attributes\":{\"indent\":1},\"insert\":\"\\n\"},{\"insert\":\"indent2\"},{\"attributes\":{\"indent\":2},\"insert\":\"\\n\"},{\"insert\":\"indent3\"},{\"attributes\":{\"indent\":3},\"insert\":\"\\n\"},{\"insert\":\"indent2\"},{\"attributes\":{\"indent\":2},\"insert\":\"\\n\"},{\"attributes\":{\"link\":\"http://wwwww.linkwwww\"},\"insert\":\"link\"},{\"insert\":\"\\n\"}]}"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val converter  = ConverterDeltaToSpanned()
        text.text = converter.convert(Delta(sampleJson))
    }
}
