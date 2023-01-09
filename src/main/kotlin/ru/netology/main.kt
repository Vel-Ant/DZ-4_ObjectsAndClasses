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
    val friends_only: Boolean?,  // true - если запись была создана с опцией «Только для друзей»
    val can_pin: Boolean?,   // Информация о том, может ли текущий пользователь закрепить запись
    val can_delete: Boolean?,    // Информация о том, может ли текущий пользователь удалить запись
    val can_edit: Boolean?,  // Информация о том, может ли текущий пользователь редактировать запись
    val attachments : Array<Attachment>? = emptyArray(),    // Вложение
    val date: Long   // Время публикации записи в формате unixtime
)

data class User(
    val id: Int,    // Идентификатор пользователя
    val first_name: String, // Имя
    val last_name: String   // Фамилия
)

object UserAdd {

    private var users = emptyArray<User>()
    private var lastId = 0

    fun add(user: User): User {
        users += user.copy(id = ++lastId)
        return users.last()
    }

    fun printAll() {
        for (user in users) {
            println(user)
        }
    }
}

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
    var userRepost = 0  // репост записи текущего пользователя;

    fun calculation(user: User) {
        count++
        userRepost = user.id
    }

    fun user_reposted(user: User): Boolean {    // наличие репоста от текущего пользователя
        return userRepost == user.id
    }

    fun clear() {
        count = 0
    }
}

object views {      // Информация о просмотрах записи

    var count = 0     // число просмотров записи

    fun calculation(userId: User) {
        count++
    }

    fun clear() {
        count = 0
    }
}

fun main() {

    val video1 = Video(15616, 154, "летящий самолет", "летящий самолет")
    val audio1 = Audio(4234, 4543, "Depp Purple", "Smoke On The Water")

    val attachmentVideo1 = VideoAttachment(video1)
    val attachmentAudio1 = AudioAttachment(audio1)

    val post = Post(1, 1, 2, "Bla-Bla", true, true, false, null, arrayOf(attachmentVideo1, attachmentAudio1), timestamp)
    WallService.add(post)
    WallService.add(post)
    WallService.printAll()
    println(WallService.update(Post(2, 1, 3, "Update Bla-Bla", true, true, false, false, arrayOf(attachmentVideo1, attachmentAudio1), timestamp)))
    WallService.printAll()

    val user = User(100, "Anton", "Velasco")
    UserAdd.add(user)
    UserAdd.printAll()
    println("Кол-во просмотров поста: ${views.count}")
    views.calculation(user)
    println("Кол-во просмотров поста: ${views.count}")

    println("Кол-во репостов: ${reposts.count}")
    reposts.calculation(user)
    println("Кол-во репостов: ${reposts.count}")
}