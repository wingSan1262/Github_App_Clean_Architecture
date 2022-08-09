package vanrrtech.app.prodiaappsample.base_components.UtilServices.LoginHandler

class RandomHandler() {

    val mImageList = ArrayList<String>()
    val mAddressList = ArrayList<String>()

    init {
        mImageList.add("Director of Netshare Company")
        mImageList.add("Director of Mihoyo Company")
        mImageList.add("Simple minded android developer, love to sleep")
        mImageList.add("I'm ajaib eng, sleep while i code, code while i sleep")
        mImageList.add("nah just see my repo broo . . .")

        mAddressList.add("Taipei, Taiwan")
        mAddressList.add("Depok, Jawa Barat")
        mAddressList.add("Oslo, Poland")
        mAddressList.add("Kyoto, Japan")
        mAddressList.add("Hirooka, Japan")
    }

    fun randomBioDescGenerator() : String {
        val index = (Math.random() * mImageList.size).toInt()
        return mImageList.get(index)
    }

    fun randomLocationDescGenerator() : String {
        val index = (Math.random() * mAddressList.size).toInt()
        return mAddressList.get(index)
    }

    fun randomImageGenerator() : String {
        val index = (Math.random() * mImageList.size).toInt()
        return mImageList.get(index)
    }
}