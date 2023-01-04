package crash.gocolor.gowebs.csvapplication

import java.io.*
import java.lang.Boolean
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CsvProcessor {
    fun getUsers(file: File?): List<User> {
        val users: MutableList<User> = ArrayList()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        try {
            val br = BufferedReader(FileReader(file))
            var line: String
            var iteration = 0
            while (br.readLine().also { line = it } != null) {
                val user = User()
                //skip the first row since its a header
                if (iteration == 0) {
                    iteration++
                    continue
                }
                //separator
                val tokens = line.split(",").toTypedArray()
                user.fullNames = tokens[0]
                user.dateOfBirth = simpleDateFormat.parse(tokens[1])
                user.height = tokens[2].toDouble()
                user.isActive = Boolean.parseBoolean(tokens[3])
                user.roles = ArrayList(Arrays.asList(*tokens[4].split(",").toTypedArray()))
                users.add(user)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return users
    }
}