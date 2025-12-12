package edu.ucne.tasktally.utils

import android.util.Log
import edu.ucne.tasktally.R

/**
 * Utilidad para manejar los recursos de imágenes de tareas de manera centralizada.
 * Evita la duplicación de código y mejora la mantenibilidad.
 */
@Suppress("unused")
object ImageResourceUtils {

    private const val TAG = "ImageResourceUtils"

    /**
     * Mapa de nombres de imágenes a sus recursos correspondientes.
     * Organizado por categorías para mejor mantenimiento.
     */
    private val imageResourceMap = mapOf(
        // Plantas y naturaleza
        "img0_yellow_tree" to R.drawable.img0_yellow_tree,
        "img1_purple_vines" to R.drawable.img1_purple_vines,
        "img2_little_bush" to R.drawable.img2_little_bush,
        "img3_little_plant" to R.drawable.img3_little_plant,
        "img5_purple_flower" to R.drawable.img5_purple_flower,
        "img6_purple_plant" to R.drawable.img6_purple_plant,
        "img8_green_leaves" to R.drawable.img8_green_leaves,
        "img9_color_leaves" to R.drawable.img9_color_leaves,

        // Objetos y utensilios
        "img10_batteries" to R.drawable.img10_batteries,
        "img11_boxes" to R.drawable.img11_boxes,
        "img12_calendar" to R.drawable.img12_calendar,
        "img13_chocolate" to R.drawable.img13_chocolate,
        "img14_clock" to R.drawable.img14_clock,
        "img15_coffee_cup" to R.drawable.img15_coffee_cup,
        "img16_coffee_machine" to R.drawable.img16_coffee_machine,
        "img16_dishes" to R.drawable.img16_dishes,
        "img17_doughnut" to R.drawable.img17_doughnut,
        "img18_doughnut" to R.drawable.img18_doughnut,
        "img19_files" to R.drawable.img19_files,
        "img20_folder" to R.drawable.img20_folder,
        "img21_food" to R.drawable.img21_food,
        "img22_hamburguer" to R.drawable.img22_hamburguer,
        "img23_ice_cream" to R.drawable.img23_ice_cream,
        "img24_mobile_phone" to R.drawable.img24_mobile_phone,
        "img25_notebook" to R.drawable.img25_notebook,
        "img26_pancakes" to R.drawable.img26_pancakes,
        "img27_pizza" to R.drawable.img27_pizza,
        "img28_pizza_slice" to R.drawable.img28_pizza_slice,
        "img29_pudding" to R.drawable.img29_pudding,
        "img30_recycle_bin" to R.drawable.img30_recycle_bin
    )

    /**
     * Mapa de nombres de imágenes a sus nombres de visualización amigables.
     * Utilizado para mostrar nombres legibles en la UI.
     */
    private val imageDisplayNameMap = mapOf(
        // Plantas y naturaleza
        "img0_yellow_tree" to "🌳 Árbol amarillo",
        "img1_purple_vines" to "🌿 Vines moradas",
        "img2_little_bush" to "🌱 Arbusto",
        "img3_little_plant" to "🪴 Plantita",
        "img5_purple_flower" to "💐 Flor morada",
        "img6_purple_plant" to "🪻 Planta morada",
        "img8_green_leaves" to "🍃 Hojas verdes",
        "img9_color_leaves" to "🍂 Hojas colores",

        // Objetos y utensilios
        "img10_batteries" to "🔋 Baterías",
        "img11_boxes" to "📦 Cajas",
        "img12_calendar" to "📅 Calendario",
        "img13_chocolate" to "🍫 Chocolate",
        "img14_clock" to "⏰ Reloj",
        "img15_coffee_cup" to "☕ Café",
        "img16_coffee_machine" to "☕ Cafetera",
        "img16_dishes" to "🍽️ Platos",
        "img17_doughnut" to "🍩 Dona",
        "img18_doughnut" to "🍩 Dona 2",
        "img19_files" to "📁 Archivos",
        "img20_folder" to "📂 Carpeta",
        "img21_food" to "🍱 Comida",
        "img22_hamburguer" to "🍔 Hamburguesa",
        "img23_ice_cream" to "🍦 Helado",
        "img24_mobile_phone" to "📱 Teléfono",
        "img25_notebook" to "📓 Cuaderno",
        "img26_pancakes" to "🥞 Pancakes",
        "img27_pizza" to "🍕 Pizza",
        "img28_pizza_slice" to "🍕 Pizza slice",
        "img29_pudding" to "🍮 Pudín",
        "img30_recycle_bin" to "♻️ Reciclaje"
    )

    /**
     * Obtiene el ID del recurso drawable de forma segura.
     *
     * @param imageName El nombre de la imagen (puede ser null o vacío)
     * @return El ID del recurso drawable o null si no existe
     */
    fun getDrawableResourceId(imageName: String?): Int? {
        if (imageName.isNullOrBlank()) {
            Log.d(TAG, "Nombre de imagen vacío o nulo")
            return null
        }

        return try {
            val resourceId = imageResourceMap[imageName]
            if (resourceId == null) {
                Log.w(TAG, "Imagen no encontrada en el mapa de recursos: $imageName")
            } else {
                Log.d(TAG, "Imagen encontrada: $imageName -> $resourceId")
            }
            resourceId
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el recurso drawable para: $imageName", e)
            null
        }
    }

    /**
     * Verifica si existe un recurso drawable para el nombre dado.
     *
     * @param imageName El nombre de la imagen a verificar
     * @return true si existe el recurso, false en caso contrario
     */
    fun hasImageResource(imageName: String?): Boolean {
        return !imageName.isNullOrBlank() && imageResourceMap.containsKey(imageName)
    }

    /**
     * Obtiene todos los nombres de imágenes disponibles.
     *
     * @return Lista de nombres de imágenes disponibles
     */
    fun getAvailableImageNames(): List<String> {
        return imageResourceMap.keys.toList()
    }

    /**
     * Obtiene una imagen aleatoria del conjunto disponible.
     *
     * @return Un nombre de imagen aleatoria
     */
    fun getRandomImageName(): String {
        val names = imageResourceMap.keys.toList()
        return names.random()
    }

    /**
     * Obtiene imágenes por categoría.
     *
     * @param category La categoría de imágenes ("plantas" o "objetos")
     * @return Lista de nombres de imágenes de la categoría especificada
     */
    fun getImagesByCategory(category: String): List<String> {
        return when (category.lowercase()) {
            "plantas", "naturaleza" -> imageResourceMap.keys.filter {
                it.startsWith("img0_") || it.startsWith("img1_") || it.startsWith("img2_") ||
                        it.startsWith("img3_") || it.startsWith("img5_") || it.startsWith("img6_") ||
                        it.startsWith("img8_") || it.startsWith("img9_")
            }

            "objetos", "utensilios" -> imageResourceMap.keys.filter {
                it.startsWith("img1") && !it.startsWith("img1_") ||
                        it.startsWith("img2") && !it.startsWith("img2_") ||
                        it.startsWith("img3") && !it.startsWith("img3_")
            }

            else -> imageResourceMap.keys.toList()
        }
    }

    /**
     * Obtiene el nombre de visualización amigable para una imagen.
     *
     * @param imageName El nombre de la imagen
     * @return El nombre de visualización o el nombre original si no se encuentra
     */
    fun getImageDisplayName(imageName: String): String {
        return imageDisplayNameMap[imageName] ?: imageName
    }

    /**
     * Obtiene todos los pares de imagen disponibles (nombre, nombre de visualización).
     *
     * @return Lista de pares (imageName, displayName)
     */
    fun getAvailableImagePairs(): List<Pair<String, String>> {
        return imageResourceMap.keys.mapNotNull { imageName ->
            val displayName = imageDisplayNameMap[imageName]
            if (displayName != null) {
                imageName to displayName
            } else null
        }
    }

    /**
     * Valida y obtiene un recurso drawable con fallback.
     *
     * @param imageName El nombre de la imagen principal
     * @param fallbackImageName El nombre de la imagen de respaldo (opcional)
     * @return El ID del recurso drawable o null si ninguno existe
     */
    fun getDrawableResourceIdWithFallback(
        imageName: String?,
        fallbackImageName: String? = null
    ): Int? {
        return getDrawableResourceId(imageName)
            ?: getDrawableResourceId(fallbackImageName)
    }
}

