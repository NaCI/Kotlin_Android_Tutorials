package com.test.rxjavathrottleallclicks

const val CLICK_THROTTLE_DURATION: Long = 2000 // 300 millisecond is ideal
const val MARKDOWN_LINK_REGEX = "\\[([^\\[\\]]*)\\]\\((.*?)\\)"
const val MARKDOWN_FORMATTED_TEXT =
    "[Lorem](https://twitter.com) Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's [standard](https://google.com) dummy text ever since the 1500s, " +
            "when an unknown printer took a galley of type and scrambled it to make a type " +
            "specimen book. It has [survived](https://nba.com) not only five centuries, but also the leap into " +
            "electronic typesetting, remaining essentially unchanged. It was popularised in the " +
            "1960s with the release of Letraset sheets [containing](https://youtube.com) Lorem Ipsum passages, and " +
            "more recently with desktop publishing software like Aldus PageMaker including " +
            "versions of Lorem Ipsum."