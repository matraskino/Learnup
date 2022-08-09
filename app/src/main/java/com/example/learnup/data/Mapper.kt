package com.example.learnup.data

import com.example.learnup.data.model.LearnItemData
import com.example.learnup.domain.models.ItemLearn

class Mapper {
    fun LearnItemToData(learnItem: ItemLearn): LearnItemData {
        return LearnItemData(
            id = learnItem.id,
            learnWord = learnItem.learnWord,
            description = learnItem.description,
            isChecked = learnItem.isChecked,
            link = learnItem.link,
            extraDescription = learnItem.extraDescription,
            topic = learnItem.topic

        )
    }

    fun LearnItemToDomain(learnItem:LearnItemData): ItemLearn {
        return ItemLearn(
            id = learnItem.id,
            learnWord = learnItem.learnWord,
            description = learnItem.description,
            isChecked = learnItem.isChecked,
            link = learnItem.link,
            extraDescription = learnItem.extraDescription,
            topic = learnItem.topic

        )
    }
}