package com.bignerdranch.android.petsaveapp.core.domain.repositories

class UserRepository {
  companion object {
    fun createDataSource(context: Context, outFile: File, password: ByteArray) {
      val inputStream = context.assets.open("users.xml")
      val serializer = Persister()
      val users = try {
        serializer.read(Users::class.java, inputStream)
      } catch (e: Exception) {
        null
      }
      users?.list?.let { // 1
        val userList = ArrayList(it) as? ArrayList // 2
        if (userList is ArrayList<User>) { // 3
          val firstUser = userList.first() as? User
          if (firstUser is User) { // 4
            firstUser.password = Base64.encodeToString(password, Base64.NO_WRAP)
            val fileOutputStream = FileOutputStream(outFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(userList)
            // 5
            objectOutputStream.close()
            fileOutputStream.close()
          }
        }
      }
      inputStream.close()
    }
  }
}