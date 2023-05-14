package com.lumu.flashcards

enum class Category(
    var chapterList: List<String>
){
    Subjects(listOf(
        "Algebra", "Analysis", "Smooth Manifolds", "Algebraic Topology"
    )),
    Topics(listOf(
        "Algebraic Geometry", "Character Theory",
        "Fundamental Group",
        "Hilbert Space", "Normed Space",
        "Singular Homology",
        "Sylow Theorem"
    )),
    Exercises(listOf(
        "Functional Analysis",
        "Group Theory",
        "Lp-Spaces", "Measure Theory",
    )),
    Everything(listOf(
    ))
}
