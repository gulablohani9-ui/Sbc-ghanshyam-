package com.gulab.sarvatobhadra

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {
    private val nak = arrayOf(
        "अश्विनी","भरणी","कृत्तिका","रोहिणी","मृगशीर्ष","आर्द्रा","पुनर्वसु","पुष्य",
        "आश्लेषा","मघा","पूर्व फाल्गुनी","उत्तर फाल्गुनी","हस्त","चित्रा","स्वाती","विशाखा",
        "अनुराधा","ज्येष्ठा","मूल","पूर्वाषाढ़ा","उत्तराषाढ़ा","अभिजित","श्रवण","धनिष्ठा",
        "शतभिषा","पूर्व भाद्रपद","उत्तर भाद्रपद","रेवती"
    )
    private val rashi = arrayOf("मेष","वृषभ","मिथुन","कर्क","सिंह","कन्या","तुला","वृश्चिक","धनु","मकर","कुंभ","मीन")
    private val tithi = arrayOf("नन्दा","भद्रा","जया","रिक्ता","पूर्णा")
    private val akshar = arrayOf("क","ख","ग","घ","च","छ","ज","झ","ट","ठ","ड","ढ","त","थ","द","ध","न","प","फ","ब")
    private val vowels = arrayOf("अ","आ","इ","ई","उ","ऊ","ऋ","ए","ऐ","ओ","औ","अं","अः","लृ","लॄ","—")

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(18,18,18,24) }
        val title = TextView(this).apply { text="🌹 Gulab Sarvatobhadra"; textSize=25f; setTextColor(Color.rgb(139,30,63)); gravity=Gravity.CENTER; setPadding(0,8,0,18) }
        root.addView(title)
        root.addView(TextView(this).apply { text="Birth details"; textSize=18f })
        val dob=EditText(this).apply { hint="Date of Birth  DD/MM/YYYY"; inputType=2 }
        val tob=EditText(this).apply { hint="Time of Birth  HH:MM"; inputType=2 }
        val place=EditText(this).apply { hint="Birth Place" }
        root.addView(dob); root.addView(tob); root.addView(place)
        val btn=Button(this).apply { text="Generate Sarvatobhadra Chakra" }
        root.addView(btn)
        val status=TextView(this).apply { text="9×9 = 81 cells • Nakshatra • Akshara • Rashi • Tithi • Vedha"; setPadding(0,12,0,12) }
        root.addView(status)
        val grid=GridLayout(this).apply { columnCount=9; rowCount=9 }
        root.addView(grid)
        btn.setOnClickListener {
            grid.removeAllViews()
            buildChakra(grid)
            status.text="Chakra generated. Tap any cell for its details."
        }
        scroll.addView(root); setContentView(scroll)
    }

    private fun cell(text:String, row:Int, col:Int): TextView {
        return TextView(this).apply {
            this.text=text; textSize=10f; gravity=Gravity.CENTER
            setTextColor(Color.rgb(45,35,35)); background=getDrawable(com.gulab.sarvatobhadra.R.drawable.bg_cell)
            setPadding(2,2,2,2)
            setOnClickListener { Toast.makeText(context, text, Toast.LENGTH_SHORT).show() }
            layoutParams=GridLayout.LayoutParams().apply { width=0; height=58; columnSpec=GridLayout.spec(col,1f); rowSpec=GridLayout.spec(row) }
        }
    }

    private fun buildChakra(grid: GridLayout) {
        val board=Array(9){Array(9){""}}
        // Classical-style five nested rings: 28 nakshatras, 20 consonants, 12 signs, 5 tithi groups, centre.
        val outer = mutableListOf<Pair<Int,Int>>()
        for(c in 1..7) outer.add(0 to c)
        for(r in 1..7) outer.add(r to 8)
        for(c in 7 downTo 1) outer.add(8 to c)
        for(r in 7 downTo 1) outer.add(r to 0)
        for(i in outer.indices) board[outer[i].first][outer[i].second]=nak[i]

        val inner2=mutableListOf<Pair<Int,Int>>()
        for(c in 2..6) inner2.add(1 to c)
        for(r in 2..6) inner2.add(r to 7)
        for(c in 6 downTo 2) inner2.add(7 to c)
        for(r in 6 downTo 2) inner2.add(r to 1)
        for(i in inner2.indices) board[inner2[i].first][inner2[i].second]=akshar[i]

        val inner3=mutableListOf<Pair<Int,Int>>()
        for(c in 3..5) inner3.add(2 to c)
        for(r in 3..5) inner3.add(r to 6)
        for(c in 5 downTo 3) inner3.add(6 to c)
        for(r in 5 downTo 3) inner3.add(r to 2)
        for(i in inner3.indices) board[inner3[i].first][inner3[i].second]=rashi[i]

        val inner4=listOf(3 to 3,3 to 5,5 to 5,5 to 3)
        for(i in inner4.indices) board[inner4[i].first][inner4[i].second]=tithi[i]
        board[4][4]="पूर्णा"

        for(r in 0..8) for(c in 0..8) {
            var s=board[r][c]
            if(s.isEmpty()) s=vowels[(r*9+c)%vowels.size]
            grid.addView(cell(s,r,c))
        }
    }
}
