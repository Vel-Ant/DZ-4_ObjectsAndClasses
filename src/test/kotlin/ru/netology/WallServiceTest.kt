package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.WallService.add
import ru.netology.WallService.createComment
import ru.netology.WallService.update

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addNewPost() {
        var posts = add(Post(1, 1, 1, "Bla-Bla", true, true, false, false, null, timestamp))

        val result = posts.id

        assertEquals(1, result)
    }

    @Test
    fun updatePostFalse() {
        var post = add(Post(1, 1, 1, "Bla-Bla", true, true, false, false, null, timestamp))
        var newPost = Post(2, 1, 3, "Update Bla-Bla", true, true, false, false, null, timestamp)

        val result = update(newPost)

        assertEquals(false, result)
    }

    @Test
    fun updatePostTrue() {
        var post = add(Post(1, 1, 1, "Bla-Bla", true, true, false, false, null, timestamp))
        var newPost = Post(1, 1, 3, "Update Bla-Bla", true, true, false, false, null, timestamp)

        val result = update(newPost)

        assertEquals(true, result)
    }

    @Test
    fun createCommentTestCorrect() {
        var post = add(Post(1, 1, 1, "Bla-Bla", true, true, false, false, null, timestamp))
        var comment = Comment(1, 2, "Комментарий", timestamp)

        val result = createComment(1, comment)

        assertEquals(Comment(1, 2, "Комментарий", timestamp), result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        var post = add(Post(1, 1, 1, "Bla-Bla", true, true, false, false, null, timestamp))
        var comment = Comment(1, 2, "Комментарий", timestamp)

        val result = createComment(2, comment)
    }
}