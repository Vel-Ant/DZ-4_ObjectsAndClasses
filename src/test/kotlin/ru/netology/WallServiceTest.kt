package ru.netology

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.netology.WallService.add
import ru.netology.WallService.createComment
import ru.netology.WallService.findById
import ru.netology.WallService.reportComment
import ru.netology.WallService.update

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addNewPost() {
        val post1 = Post(0, 1, 1, "Bla-Bla")

        val result = add(post1)

        assertEquals(Post(1, 1, 1, "Bla-Bla"), result)
    }

    @Test
    fun updatePostFalse() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))

        val result = update(Post(2, 1, 3, "Update Bla-Bla"))

        assertEquals(false, result)
    }

    @Test
    fun updatePostTrue() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))

        val result = update(Post(1, 1, 3, "Update Bla-Bla"))

        assertEquals(true, result)
    }

    @Test
    fun createCommentTest() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))
        val comment1 = Comment(1, 2, "Комментарий")

        val result = createComment(1, comment1)

        assertEquals(Comment(1, 2, "Комментарий"), result)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentException() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))
        val comment1 = Comment(1, 2, "Комментарий")

        val result = createComment(2, comment1)
    }

    @Test
    fun reportCommentTestComment_id() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))
        val comment1 = createComment(1, Comment(0, 2, "Комментарий"))

        val result = reportComment(1, 3)

        assertEquals(true, result)
    }

    @Test(expected = ReasonNotFoundException::class)
    fun reportCommentTestReasonException() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))
        val comment1 = createComment(1, Comment(0, 2, "Комментарий"))

        val result = reportComment(1, 9)
    }

    @Test(expected = CommentNotFoundException::class)
    fun reportCommentException() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))
        val comment1 = createComment(1, Comment(0, 2, "Комментарий"))

        val result = reportComment(2, 3)
    }

    @Test
    fun findByIdTest() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))

        val result = findById(1)

        assertEquals(Post(1, 1, 1, "Bla-Bla"), result)
    }

    @Test(expected = PostNotFoundException::class)
    fun findByIdException() {
        val post1 = add(Post(0, 1, 1, "Bla-Bla"))

        val result = findById(5)
    }
}