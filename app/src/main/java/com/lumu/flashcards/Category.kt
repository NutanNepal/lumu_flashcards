package com.lumu.flashcards

class Category(
    var categoryName: String,
    private var chapterList: List<String>
){
    override fun toString(): String {
        return "Category(categoryName='$categoryName', chapterList=$chapterList)"
    }
}

object CategoryData {
    private fun getCategoryList(): List<Category> {
        val subjectsCategory = Category("Subjects", listOf("Algebra", "Geometry", "Calculus"))
        val topicsCategory = Category("Topics", listOf("Physics", "Chemistry", "Biology"))
        val exercisesCategory = Category("Exercises", listOf("English", "Spanish"))
        val everythingCategory = Category("Everything", listOf("French"))
        val lastCategory = Category("last one", listOf("Algebra", "Geometry", "Calculus"))

        return listOf(subjectsCategory, topicsCategory, exercisesCategory, everythingCategory, lastCategory)
    }
    fun getCategoryNames(): List<String> {
        val categoryList = getCategoryList()
        val categoryNames = mutableListOf<String>()
        for (category in categoryList) {
            categoryNames.add(category.categoryName)
        }
        return categoryNames
    }
}
