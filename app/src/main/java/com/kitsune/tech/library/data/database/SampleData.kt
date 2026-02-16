package com.kitsune.tech.library.data.database

object SampleData {
    fun getSampleBooks(): List<Book> = listOf(
        Book(
            title = "The Fourth Wing",
            author = "Rebecca Yarros",
            description = "Twenty-year-old Violet Sorrengail was supposed to enter the Scribe Quadrant, living a quiet life among books and history. Now, the commanding general—also known as her tough-as-talons mother—has ordered Violet to join the hundreds of candidates striving to become the elite of Navarre: dragon riders.",
            genre = "Fantasy",
            publishedYear = 2023,
            totalPages = 498,
            rating = 4.5f
        ),
        Book(
            title = "Frankenstein",
            author = "Mary Shelley",
            description = "Frankenstein tells the story of Victor Frankenstein, a young scientist who creates a sapient creature in an unorthodox scientific experiment. Shelley started writing the story when she was 18, and the first edition was published anonymously in London on 1 January 1818.",
            genre = "Gothic Fiction",
            publishedYear = 1818,
            totalPages = 280,
            rating = 4.0f
        ),
        Book(
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            description = "Bilbo Baggins is a hobbit who enjoys a comfortable, unambitious life, rarely traveling any farther than his pantry or cellar. But his contentment is disturbed when the wizard Gandalf and a company of dwarves arrive on his doorstep one day to whisk him away on an adventure.",
            genre = "Fantasy",
            publishedYear = 1937,
            totalPages = 310,
            rating = 4.8f
        ),
        Book(
            title = "1984",
            author = "George Orwell",
            description = "A dystopian social science fiction novel and cautionary tale. It centers on the consequences of totalitarianism, mass surveillance and repressive regimentation of people and behaviors within society.",
            genre = "Dystopian Fiction",
            publishedYear = 1949,
            totalPages = 328,
            rating = 4.6f
        ),
        Book(
            title = "Pride and Prejudice",
            author = "Jane Austen",
            description = "The story follows the main character, Elizabeth Bennet, as she deals with issues of manners, upbringing, morality, education, and marriage in the society of the landed gentry of the British Regency.",
            genre = "Romance",
            publishedYear = 1813,
            totalPages = 432,
            rating = 4.4f
        ),
        Book(
            title = "The Great Gatsby",
            author = "F. Scott Fitzgerald",
            description = "Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.",
            genre = "Fiction",
            publishedYear = 1925,
            totalPages = 180,
            rating = 4.2f
        ),
        Book(
            title = "To Kill a Mockingbird",
            author = "Harper Lee",
            description = "The story takes place during three years of the Great Depression in the fictional town of Maycomb, Alabama. It focuses on six-year-old Jean Louise Finch, who lives with her older brother Jem and their widowed father Atticus, a middle-aged lawyer.",
            genre = "Southern Gothic",
            publishedYear = 1960,
            totalPages = 324,
            rating = 4.7f
        ),
        Book(
            title = "Harry Potter and the Sorcerer's Stone",
            author = "J.K. Rowling",
            description = "Harry Potter has no idea how famous he is. That's because he's being raised by his miserable aunt and uncle who are terrified Harry will learn that he's really a wizard, just as his parents were.",
            genre = "Fantasy",
            publishedYear = 1997,
            totalPages = 309,
            rating = 4.8f
        )
    )
}
