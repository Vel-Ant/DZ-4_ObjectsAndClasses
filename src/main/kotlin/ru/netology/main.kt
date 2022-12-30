package ru.netology

import java.time.Instant
import java.time.format.DateTimeFormatter

//  расчет текущего времени
val timestamp = System.currentTimeMillis()
val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
val sdfFormat = sdf.format(timestamp)

//  расчет World Time API
val timestampApi = DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(timestamp / 1000))

data class Post(
    val id: Int,    // Идентификатор записи
    val owner_id: Int,  // Идентификатор владельца стены, на которой размещена запись
    val from_id: Int,   // Идентификатор автора записи (от чьего имени опубликована запись)
    val text: String,   // Текст записи
    val friends_only: Boolean,  // true - если запись была создана с опцией «Только для друзей»
    val can_pin: Boolean,   // Информация о том, может ли текущий пользователь закрепить запись (true, false)
    val can_delete: Boolean,    // Информация о том, может ли текущий пользователь удалить запись (true, false)
    val can_edit: Boolean,  // Информация о том, может ли текущий пользователь редактировать запись (true, false)
    val date: Long   // Время публикации записи в формате unixtime
)

data class User(
    val id: Int,    // Идентификатор пользователя
    val first_name: String, // Имя
    val last_name: String   // Фамилия
)

object WallService {

    private var posts = emptyArray<Post>()
    private var lastId = 0

    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId)
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy()
                return true
            }
        }
        return false
    }

    fun printAll() {
        for (post in posts) {
            println(post)
        }
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }
}


object reposts {    // Информация о репостах записи («Рассказать друзьям»), объект с полями:

    var count = 0     // число пользователей, скопировавших запись;

    fun calculation() {
        count++
    }

//    fun user_reposted(user: User): Boolean{     // наличие репоста от текущего пользователя (true, false).
//
//    }

    fun clear() {
        count = 0
    }
}

object views {      // Информация о просмотрах записи. Объект с единственным полем:

    var count = 0     // число просмотров записи.

    fun calculation() {
        count++
    }

    fun clear() {
        count = 0
    }
}


fun main() {

    val post = Post(1, 1, 2, "Bla-Bla", true, true, false, false, timestamp)
    WallService.add(post)
    WallService.add(post)
    WallService.printAll()
    println(WallService.update(Post(2, 1, 3, "Update Bla-Bla", true, true, false, false, timestamp)))
    WallService.printAll()

    views.calculation()
    println(views.count)
}
