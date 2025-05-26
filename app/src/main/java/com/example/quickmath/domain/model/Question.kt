package com.example.quickmath.domain.model

interface Question {
    val parameters: List<Int>
    val template: String
    var answers: List<Answer>

    fun generateAnswers(): List<Answer>
    fun getFinalAnswer(): Answer
    fun validate(answer: Answer): Boolean {
        return answer.value == getFinalAnswer().value
    }
}

class EmptyQuestion: Question {
    override val parameters: List<Int>
        get() = listOf()
    override val template: String
        get() = ""
    override var answers: List<Answer> = generateAnswers()

    override fun generateAnswers(): List<Answer> {
        return listOf()
    }

    override fun getFinalAnswer(): Answer {
        return Answer(0)
    }
}

class AdditionQuestion(override val parameters: List<Int>) : Question {
    override val template: String
        get() = String.format("%s + %s", parameters[0], parameters[1])

    override var answers: List<Answer> = generateAnswers()

    override fun generateAnswers(): List<Answer> {
        val finalAnswer = getFinalAnswer()
        val answerChoices = mutableListOf<Int>(finalAnswer.value)
        repeat(3){
            val random = (Math.random() * finalAnswer.value * 2).toInt()
            answerChoices.add(random)
        }

        answerChoices.shuffle()
        return answerChoices.map { Answer(it) }
    }

    override fun getFinalAnswer(): Answer {
        return Answer(parameters[0] + parameters[1])
    }
}

class SubtractionQuestion(override val parameters: List<Int>) : Question {
    override val template: String
        get() = String.format("%s - %s", parameters[0], parameters[1])

    override var answers: List<Answer> = generateAnswers()

    override fun generateAnswers(): List<Answer> {
        val finalAnswer = getFinalAnswer()
        val answerChoices = mutableListOf<Int>(finalAnswer.value)
        repeat(3){
            val random = (Math.random() * finalAnswer.value * 2).toInt()
            answerChoices.add(random)
        }

        answerChoices.shuffle()
        return answerChoices.map { Answer(it) }
    }

    override fun getFinalAnswer(): Answer {
        return Answer(parameters[0] - parameters[1])
    }
}