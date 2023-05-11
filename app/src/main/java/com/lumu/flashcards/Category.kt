package com.lumu.flashcards

enum class Category(
    var chapterList: List<String>
){
    Subjects(listOf("Algebra", "Analysis", "Smooth Manifolds and Algebraic Topology")),
    Topics(listOf("Physics", "Chemistry", "Biology")),
    Exercises(listOf("English", "Spanish")),
    Everything(listOf("French"))
}
