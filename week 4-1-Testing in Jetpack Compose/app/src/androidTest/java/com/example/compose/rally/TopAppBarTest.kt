package com.example.compose.rally

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

//    @Test
//    fun rallyTopAppBarTest() {
//        val allScreens = RallyScreen.values().toList()
//
//        composeTestRule.setContent {
//            RallyTopAppBar(
//                allScreens = allScreens,
//                onTabSelected = { /*TODO*/ },
//                currentScreen = RallyScreen.Accounts
//            )
//        }
//        Thread.sleep(5000)
//    }

@Test
fun rallyTopAppBarTest_currentTabSelected() {
    val allScreens = RallyScreen.values().toList()
    composeTestRule.setContent {
        RallyTopAppBar(
            allScreens = allScreens,
            onTabSelected = { },
            currentScreen = RallyScreen.Accounts
        )
    }

    composeTestRule
        .onNodeWithContentDescription(RallyScreen.Accounts.name)
        .assertIsSelected()
}


    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNode(
                hasText(RallyScreen.Accounts.name) and
                        hasParent(
                            hasContentDescription(RallyScreen.Accounts.name)
                        ),
                useUnmergedTree = true
            )
            .assertExists()


    }

    // Chalzz 님꺼 보고 했습니다.
    @Test
    fun rallyTopAppBarTest_clickTabs(){
        val allScreens = RallyScreen.values().toList()
        var currentScreen:RallyScreen = RallyScreen.Overview
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { screen -> currentScreen = screen},
                currentScreen = RallyScreen.Accounts
            )
        }

        allScreens.forEach { screen->
            composeTestRule
                .onNodeWithContentDescription(screen.name)
                .performClick()
                assert(currentScreen == screen)
                //assert(currentScreen.name == screen.name)
        }

    }

//        @Test
//    fun rallyTopAppBarTest_TabSelected() {
//            var currentScreen:RallyScreen = RallyScreen.Overview
//            composeTestRule.setContent {
//                composeTestRule.setContent {
//                    RallyApp(currentScreen){ screen-> currentScreen = screen }
//                }
//        }
//
//            RallyScreen.values().forEach { screen->
//                composeTestRule
//                    .onNodeWithContentDescription(screen.name)
//                    .performClick()
//                assert(currentScreen == screen)
//            }
//    }
}