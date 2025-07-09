package com.example.quickmath.domain.model

interface Question {
    val parameters: List<Int>
    val template: String
    var answers: List<Answer>

    fun getFinalAnswer(): Answer
    fun generateAnswers(): List<Answer> {
        val finalAnswer = getFinalAnswer().value

        //add correct answer
        val answerChoices = mutableListOf<Int>(finalAnswer)

        //generate random answer
        repeat(3){
            val random = (Math.random() * finalAnswer * 2) - finalAnswer
            answerChoices.add(random.toInt())
        }

        //randomise choices
        answerChoices.shuffle()
        return answerChoices.map { Answer(it) }
    }
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

    override fun getFinalAnswer(): Answer {
        return Answer(0)
    }
}

class AdditionQuestion(override val parameters: List<Int>) : Question {
    override val template: String
        get() = String.format("%s + %s", parameters[0], parameters[1])

    override var answers: List<Answer> = generateAnswers()

    override fun getFinalAnswer(): Answer {
        return Answer(parameters[0] + parameters[1])
    }
}

class SubtractionQuestion(override val parameters: List<Int>) : Question {
    override val template: String
        get() = String.format("%s - %s", parameters[0], parameters[1])

    override var answers: List<Answer> = generateAnswers()

    override fun getFinalAnswer(): Answer {
        return Answer(parameters[0] - parameters[1])
    }
}

class MultiplicationQuestion(override val parameters: List<Int>) : Question {
    override val template: String
        get() = String.format("%s x %s", parameters[0], parameters[1])

    override var answers: List<Answer> = generateAnswers()

    override fun getFinalAnswer(): Answer {
        return Answer(parameters[0] * parameters[1])
    }
}

class DivisionQuestion(override val parameters: List<Int>) : Question {
    override val template: String
        get() = String.format("%s / %s", parameters[0], parameters[1])

    override var answers: List<Answer> = generateAnswers()

    override fun getFinalAnswer(): Answer {
        return Answer(parameters[0] / parameters[1])
    }
}