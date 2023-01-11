package ru.netology

sealed class Attachment(val type: String)

class AudioAttachment(val audio: Audio) : Attachment("audio") {
    override fun toString(): String {
        return "Audio = ${audio.id}"
    }
}

data class Audio(
    val id: Int? = null,                 // Идентификатор аудиозаписи
    val owner_id: Int? = null,           // Идентификатор владельца аудиозаписи
    val artist: String? = null,          // Исполнитель
    val title: String? = null            // Название композиции
)

class VideoAttachment(val video: Video) : Attachment("video") {
    override fun toString(): String {
        return "Video = ${video.id}"
    }
}

data class Video(
    val id: Int? = null,                 // Идентификатор видеозаписи
    val owner_id: Int? = null,           // Идентификатор владельца видеозаписи
    val description: String? = null,     // Текст описания видеозаписи
    val title: String? = null            // Название видеозаписи
)

class FotoAttachment(val foto: Foto) : Attachment("foto") {
    override fun toString(): String {
        return "Foto = ${foto.id}"
    }
}

data class Foto(
    val id: Int? = null,                 // Идентификатор фотографии
    val owner_id: Int? = null,           // Идентификатор владельца фотографии
    val user_id: Int? = null,            // Идентификатор пользователя, загрузившего фото
    val album_id: Int? = null,           // Идентификатор альбома, в котором находится фотография
    val text: String? = null,            // Текст описания фотографии
)

class FileAttachment(val file: File) : Attachment("file") {

    override fun toString(): String {
        return "File = ${file.id}"
    }
}

data class File(
    val id: Int? = null,                 // Идентификатор файла
    val owner_id: Int? = null,           // Идентификатор пользователя, загрузившего файл
    val size: Int? = null,               // Размер файла в байтах
    val title: String? = null,           // Название файла
    val date: Long = timestamp          // Дата добавления в формате Unixtime
)

class StickerAttachment(val sticker: Sticker) : Attachment("sticker") {

    override fun toString(): String {
        return "Sticker = ${sticker.sticker_id}"
    }
}

data class Sticker(
    val product_id: Int? = null,                         // Идентификатор набора
    val sticker_id: Int? = null,                         // Идентификатор стикера
    val animation_url: String? = null,                   // URL анимации стикера
    val images: Array<Images> = emptyArray(),            // Изображения для стикера (с прозрачным фоном)
    val images_with_background: Array<Images_with_background> = emptyArray()          // Изображения для стикера (с непрозрачным фоном)
)

data class Images(
    val url: String? = null,             // URL копии изображения
    val width: Int? = null,              // ширина копии в px
    val height: Int? = null             // высота копии в px
)

data class Images_with_background(
    val url: String? = null,             // URL копии изображения
    val width: Int? = null,              // ширина копии в px
    val height: Int? = null             // высота копии в px
)