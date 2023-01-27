package ru.netology

import java.time.Instant
import java.time.format.DateTimeFormatter

//  расчет текущего времени
val timestamp = System.currentTimeMillis()
val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
val sdfFormat = sdf.format(timestamp)

//  расчет World Time API
val timestampApi = DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(timestamp / 1000))

class PostNotFoundException(message: String): RuntimeException(message)
class CommentNotFoundException(message: String): RuntimeException(message)
class ReasonNotFoundException(message: String): RuntimeException(message)

data class Post(
    var post_id: Int,    // Идентификатор записи
    val owner_id: Int,  // Идентификатор владельца стены, на которой размещена запись
    val from_id: Int,   // Идентификатор автора записи (от чьего имени опубликована запись)
    val text: String,   // Текст записи
    val friends_only: Boolean = false,  // true - если запись была создана с опцией «Только для друзей»
    val can_pin: Boolean = true,   // Информация о том, может ли текущий пользователь закрепить запись
    val can_delete: Boolean = true,    // Информация о том, может ли текущий пользователь удалить запись
    val can_edit: Boolean = true,  // Информация о том, может ли текущий пользователь редактировать запись
    val attachments : Array<Attachment> = emptyArray(),    // Вложение
    val date: Long = timestamp  // Время публикации записи в формате unixtime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (post_id != other.post_id) return false
        if (owner_id != other.owner_id) return false
        if (from_id != other.from_id) return false
        if (text != other.text) return false
        if (friends_only != other.friends_only) return false
        if (can_pin != other.can_pin) return false
        if (can_delete != other.can_delete) return false
        if (can_edit != other.can_edit) return false
        if (!attachments.contentEquals(other.attachments)) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = post_id
        result = 31 * result + owner_id
        result = 31 * result + from_id
        result = 31 * result + text.hashCode()
        result = 31 * result + friends_only.hashCode()
        result = 31 * result + can_pin.hashCode()
        result = 31 * result + can_delete.hashCode()
        result = 31 * result + can_edit.hashCode()
        result = 31 * result + attachments.contentHashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}

data class Comment(
    val comment_id: Int,    // Идентификатор комментария
    val from_id: Int? = null,   // Идентификатор автора комментария
    val text: String? = null,   // Текст комментария
    val date: Long = timestamp   // Дата создания комментария в формате Unixtime
)
data class User(
    val uid: Int,    // Идентификатор пользователя
    val first_name: String, // Имя
    val last_name: String   // Фамилия
)

data class Complaint(
    val complaintComments : MutableList<Comment> = mutableListOf()
)

object UserAdd {

    private var users = mutableListOf<User>()
    private var lastId = 0

    fun addUser(first_name: String, last_name: String): User {
        users.add(User(++lastId, first_name = first_name, last_name = last_name))
        return users.last()
    }

    fun printAll() = users.forEach(:: println)
}

object WallService {

    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var complaints = mutableMapOf<Int, Complaint>()
    private var lastPostId = 0
    private var lastCommentId = 0

    fun add(post: Post): Post {
        posts += post.copy(post_id = ++lastPostId)
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.post_id == newPost.post_id) {
                posts[index] = newPost.copy()
                return true
            }
        }
        return false
    }

    fun createComment(post_id: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.post_id == post_id) {
                comments += comment.copy(comment_id = ++lastCommentId)
                return comments.last()
            }
        }
        throw PostNotFoundException("Post with id $post_id not found")
    }

    fun findById(post_id: Int): Post {
      return posts.find { it.post_id == post_id} ?: throw PostNotFoundException("Post with id $post_id not found")
    }

    fun removeById(id: Int): Boolean {
        TODO("Unimplemented")
    }

    fun reportComment(comment_id: Int, reason: Int): Boolean {

        val reasonValue = when (reason) {
            0 -> "спам"
            1 -> "детская порнография"
            2 -> "экстремизм"
            3 -> "насилие"
            4 -> "пропаганда наркотиков"
            5 -> "материал для взрослых"
            6 -> "оскорбление"
            8 -> "призывы к суициду"
            else -> throw ReasonNotFoundException("Reason with # $reason not found")
        }

        for (comment in comments) {
            if (comment.comment_id == comment_id) {
                complaints.getOrPut(reason) {Complaint()}.complaintComments += comment
                println("Комментарий помечен жалобой: \"$reasonValue\"")
                return true
            }
        }
        throw CommentNotFoundException("Comment with id $comment_id not found")
    }

    fun printAllPosts() = posts.forEach(:: println)

    fun printAllComments() = comments.forEach(:: println)

    fun printAllComplaints() = complaints.forEach(:: println)
    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        lastPostId = 0
        lastCommentId = 0
    }
}

object reposts {    // Информация о репостах записи («Рассказать друзьям»), объект с полями:

    var count = 0     // число пользователей, скопировавших запись;
    var userRepost = 0  // репост записи текущего пользователя;

    fun calculation(uid: Int) {
        count++
        userRepost = uid
    }

    fun user_reposted(uid: Int): Boolean { return userRepost == uid }   // наличие репоста от текущего пользователя

    fun clear() { count = 0 }
}

object views {      // Информация о просмотрах записи

    var count = 0     // число просмотров записи

    fun calculation(uid: Int) { count++ }

    fun clear() { count = 0 }
}

fun main() {

    val video1 = Video(15616, 154, "летящий самолет", "летящий самолет")
    val audio1 = Audio(4234, 4543, "Deep Purple", "Smoke On The Water")

    val attachmentVideo1 = VideoAttachment(video1)
    println(attachmentVideo1.type)
    val attachmentAudio1 = AudioAttachment(audio1)
    println(attachmentAudio1.type)

    val post = Post(0, 1, 2, "Bla-Bla", attachments = arrayOf(attachmentVideo1, attachmentAudio1))
    println(WallService.add(post))
    println(WallService.add(post))
    WallService.update(Post(2, 1, 3, "Update Bla-Bla", attachments = arrayOf(attachmentVideo1, attachmentAudio1)))
    WallService.printAllPosts()

    println(UserAdd.addUser("Anton", "Velasco"))
    println("Кол-во просмотров поста: ${views.count}")
    views.calculation(1)
    println("Кол-во просмотров поста: ${views.count}")

    println("Кол-во репостов: ${reposts.count}")
    reposts.calculation(1)
    println("Кол-во репостов: ${reposts.count}")

    WallService.printAllComments()
    println(WallService.createComment(1, Comment(0, 2, "Комментарий")))
    println(WallService.createComment(1, Comment(0, 2, "Добавлен второй комментарий")))
    println(WallService.createComment(1, Comment(0, 2, "Добавлен третий комментарий")))

    WallService.reportComment(1, 2)
    WallService.reportComment( 2, 2)
    WallService.reportComment( 3, 6)
    WallService.printAllComplaints()

    println(WallService.findById(1))
}