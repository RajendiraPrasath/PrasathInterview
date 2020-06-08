package xpertii.prasathinterview.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import xpertii.prasathinterview.model.Product

class DatabaseHandler (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "productdatabase"
        private val TABLE_CONTACTS = "cartproducttable"
        private val primary_ID = "primaryid"
        private val KEY_PRODUCT_ID = "productid"
        private val KEY_PRODUCT_IMAGE = "productimage"
        private val KEY_PRODUCT_NAME = "productname"
        private val KEY_PRODUCT_QTY = "qty"
        private val KEY_PRODUCT_PRICE = "productprice"
        private val KEY_PRODUCT_SPLPRICE = "splprice"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "(" + primary_ID + " INTEGER PRIMARY KEY," + KEY_PRODUCT_NAME + " TEXT," + KEY_PRODUCT_ID + " TEXT," + KEY_PRODUCT_PRICE + " TEXT," + KEY_PRODUCT_IMAGE + " TEXT," + KEY_PRODUCT_SPLPRICE + " TEXT," + KEY_PRODUCT_QTY + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }



    fun addCartProduct(emp: Product):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(primary_ID, emp.product_id.toInt())
        contentValues.put(KEY_PRODUCT_ID, emp.product_id)
        contentValues.put(KEY_PRODUCT_NAME, emp.name)
        contentValues.put(KEY_PRODUCT_QTY,emp.quantity )
        contentValues.put(KEY_PRODUCT_PRICE,emp.price )
        contentValues.put(KEY_PRODUCT_IMAGE,emp.image )
        contentValues.put(KEY_PRODUCT_SPLPRICE,emp.special )

        val success = db.insert(TABLE_CONTACTS, null, contentValues)

        db.close()
        return success
    }

    fun viewProduct():List<Product>{
        val productList:ArrayList<Product> = ArrayList<Product>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var productId: String
        var productName: String
        var productqty: String
        var productPrice: String
        var productImage: String
        var productSplPrice: String
        if (cursor.moveToFirst()) {
            do {
                productId = cursor.getString(cursor.getColumnIndex("productid"))
                productName = cursor.getString(cursor.getColumnIndex("productname"))
                productqty = cursor.getString(cursor.getColumnIndex("qty"))
                productPrice = cursor.getString(cursor.getColumnIndex("productprice"))
                productImage = cursor.getString(cursor.getColumnIndex("productimage"))
                productSplPrice = cursor.getString(cursor.getColumnIndex("splprice"))

                val product = Product(productName,productId,productImage,productqty,productPrice,productSplPrice)
                productList.add(product)
            } while (cursor.moveToNext())
        }
        return productList
    }

    fun updateProduct(product: Product):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_PRODUCT_ID, product.product_id)
        contentValues.put(KEY_PRODUCT_NAME, product.name)
        contentValues.put(KEY_PRODUCT_QTY,product.quantity )
        contentValues.put(KEY_PRODUCT_PRICE,product.price )
        contentValues.put(KEY_PRODUCT_IMAGE,product.image )
        contentValues.put(KEY_PRODUCT_SPLPRICE,product.special )

        val success = db.update(TABLE_CONTACTS, contentValues,"primaryid="+product.product_id,null)

        db.close()
        Log.i("update success",product.quantity)
        return success
    }

    fun deleteAllRecord()
    {
        val db = this.writableDatabase
        db.delete(TABLE_CONTACTS, null, null);
        db.close()
    }
}