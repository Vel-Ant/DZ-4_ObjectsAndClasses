package ru.netology

interface Attachment {
    val type: String
}

class AudioAttachment(type: Audio): Attachment {
        override val type: String = "audio"
        val audio: Audio = type

    override fun toString(): String {
        return "Audio = ${audio.id}"
    }
}

data class Audio (
    val id: Int? = null,                 // Идентификатор аудиозаписи
    val owner_id: Int? = null,           // Идентификатор владельца аудиозаписи
    val artist: String? = null,          // Исполнитель
    val title: String? = null           // Название композиции
)

class VideoAttachment(type: Video): Attachment {
    override val type: String = "video"
    val video: Video = type

    override fun toString(): String {
        return "Video = ${video.id}"
    }
}

data class Video (
    val id: Int? = null,                 // Идентификатор видеозаписи
    val owner_id: Int? = null,           // Идентификатор владельца видеозаписи
    val description: String? = null,     // Текст описания видеозаписи
    val title: String? = null           // Название видеозаписи
)

class FotoAttachment(type: Foto) : Attachment {
    override val type: String = "foto"
    val foto: Foto = type

    override fun toString(): String {
        return "Foto = ${foto.id}"
    }
}

data class Foto (
    val id: Int? = null,                 // Идентификатор фотографии
    val owner_id: Int? = null,           // Идентификатор владельца фотографии
    val user_id: Int? = null,            // Идентификатор пользователя, загрузившего фото
    val album_id: Int? = null,           // Идентификатор альбома, в котором находится фотография
    val text: String? = null,            // Текст описания фотографии
)

class FileAttachment(type: File) : Attachment {
    override val type: String = "file"
    val file: File = type

    override fun toString(): String {
        return "File = ${file.id}"
    }
}

data class File (
    val id: Int? = null,                 // Идентификатор файла
    val owner_id: Int? = null,           // Идентификатор пользователя, загрузившего файл
    val size: Int? = null,               // Размер файла в байтах
    val title: String? = null,           // Название файла
    val date: Long = timestamp          // Дата добавления в формате Unixtime
)

class StickerAttachment(type: Sticker): Attachment {
    override val type: String = "sticker"
    val sticker: Sticker = type

    override fun toString(): String {
        return "Sticker = ${sticker.sticker_id}"
    }
}

data class Sticker (
    val product_id: Int? = null,                         // Идентификатор набора
    val sticker_id: Int? = null,                         // Идентификатор стикера
    val animation_url: String? = null,                   // URL анимации стикера
    val images: Array<Images> = emptyArray(),           // Изображения для стикера (с прозрачным фоном)
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