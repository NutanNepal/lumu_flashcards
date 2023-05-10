package com.lumu.flashcards

enum class Category(
    public var chapterList: List<String>
){
    Subjects(listOf("Algebra", "Geometry", "Calculus")),
    Topics(listOf("Physics", "Chemistry", "Biology")),
    Exercises(listOf("English", "Spanish")),
    Everything(listOf("French"))
}

//object CategoryData {
//    private fun getCategoryList(): List<Category> {
//        val subjectsCategory = Category("Subjects", listOf("Algebra", "Geometry", "Calculus"))
//        val topicsCategory = Category("Topics", listOf("Physics", "Chemistry", "Biology"))
//        val exercisesCategory = Category("Exercises", listOf("English", "Spanish"))
//        val everythingCategory = Category("Everything", listOf("French"))
//        val lastCategory = Category("last one", listOf("Algebra", "Geometry", "Calculus"))
//
//        return listOf(subjectsCategory, topicsCategory, exercisesCategory, everythingCategory, lastCategory)
//    }
//    fun getCategoryNames(): List<String> {
//        val categoryList = getCategoryList()
//        val categoryNames = mutableListOf<String>()
//        for (category in categoryList) {
//            categoryNames.add(category.categoryName)
//        }
//        return categoryNames
//    }
//}
