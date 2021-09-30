package hu.petrik.coinflip

import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess
import kotlin.random.Random
import android.widget.Toast
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity() {

    private var dobasok: Int = 0
    private var gyozelmek: Int = 0
    private var veresegek: Int = 0
    private var fej: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Locale.setDefault(Locale("hu"))
        setContentView(R.layout.activity_main)
        init()
        buttonFej.setOnClickListener {
            tippel(true)
        }
        buttonIras.setOnClickListener {
            tippel(false)
        }
    }

    private fun szoveg(fej: Boolean): String {
        var ki = "fej"
        if (!fej) {
            ki = "írás"
        }
        return ki
    }

    private fun init() {
        fej = Random.nextBoolean()
        dobasok = 0
        gyozelmek = 0
        veresegek = 0
        lekovet()
    }

    private fun lekovet() {
        textViewDobasok.text = ("Dobások: $dobasok")
        textViewGyozelmek.text = ("Győzelem: $gyozelmek")
        textViewVeresegek.text = ("Vereség: $veresegek")
    }

    private fun lekovet(tipp: Boolean) {
        lekovet()
        Toast.makeText(applicationContext, "Eredmény: " + szoveg(fej), Toast.LENGTH_SHORT).show()
        if (dobasok > 3 && gyozelmek != veresegek) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Játék Vége")
            var eredmeny = "Győzelem"
            if (tipp == fej) {
                eredmeny = "Vereség"
            }
            builder.setMessage("$eredmeny. Új játék?")
            builder.setPositiveButton("Igen") { _, _ ->
                init()
            }
            builder.setNegativeButton("Nem") { _, _ ->
                exitProcess(0)
            }
            builder.show()
        }
    }


    private fun megjelenit(fej: Boolean, animalt: Boolean = false) {
        if (animalt) {
            var jelenAllas = false
            megjelenit(jelenAllas)
            jelenAllas = !jelenAllas
            megjelenit(jelenAllas)
        }
        if (fej) {
            imageViewErme.setImageResource(R.drawable.heads)
        } else {
            imageViewErme.setImageResource(R.drawable.tails)
        }
    }

    private fun tippel(tipp: Boolean) {
        if (tipp == fej) {
            gyozelmek++
        } else {
            veresegek++
        }
        dobasok++
        lekovet(tipp)
        megjelenit(fej, true)
        fej = Random.nextBoolean()
    }
}