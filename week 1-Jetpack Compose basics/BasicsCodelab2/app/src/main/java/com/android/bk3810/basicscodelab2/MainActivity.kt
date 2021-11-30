package com.android.bk3810.basicscodelab2

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bk3810.basicscodelab2.ui.theme.BasicsCodelab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelab2Theme {
                MyApp()

            }
//            BasicsCodelab2Theme {
//                //Text("Hello World!!!")
//                //MessageCard(Message("Android", "Jetpack Compose"))
//                Conversation(messages = SampleData.conversationSample)
//            }

//            BasicsCodelab2Theme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
        }
    }
}
@Composable
fun OnboardingScreen( onContinueClicked: () -> Unit) {

    //var shouldShowOnboarding by remember { mutableStateOf(true)}

    Surface {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }


        }

    }
}
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelab2Theme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Composable
fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true)}

    if(shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List (1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items (items = names ) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card (
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    {
        CardContent(name = name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Composable
private fun DefaultPreview() {
    BasicsCodelab2Theme {
        MyApp()
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image (
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size 40
                .size(40.dp)
                // clip image to be shape
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // 수평 공백 추가
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false)}

        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            // 수직 공백 추가
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body2,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    BasicsCodelab2Theme {
        MessageCard(
            msg = Message("Colleague", "Hey, take a look at Jetpack Compose")
        )
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { messages ->
            MessageCard(msg = messages)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    BasicsCodelab2Theme {
        Conversation(SampleData.conversationSample)
    }
}