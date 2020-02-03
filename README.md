![Example 1](images/logo.png)

# Babilonia

Babilonia is an innovative tool for language enthusiasts who want to grow and strengthen 
their vocabulary in foreign languages.

Unlike traditionnal methods, Babilonia lets you practice multiple languages *at the same time*! 
:heart_eyes:

## Features

- Lets you memorize a *maximum* amount of words in a *minimum* amount of time
- Contains clever tricks to help you remember as you're typing
- Keeps track of how many words you know in each language
- Uses a novel method based on universal ideas
- Allows you to practice any set of languages all at once!

## What's the deal?

### Practicing

The core component of Babilonia is its practice feature.

An idea shows up (in any language you want), and you get to practice it (in any other language you want).

An idea could be a thing, an expression or even a whole sentence. You're the boss!

![Example 1](images/example1.png)

### Powerful help as you're typing

Babilonia gives you feedback by coloring each letter you type in green if you're on the right track, or red otherwise.

- By helping you recall the words by yourself, your brain will more easily remember them the next time :wink:

![Example 2](images/example2.png)

### Hints go a long way

Press the Hint button to have a letter revealed to you, which may very well spark a sudden insight in your mind.

- Again, that helps your brain create neural pathways it would otherwise not if the answer
was given out to you in full!

Of course, if you really don't know, you can always just press the Full button.

### Different spellings for an easier life

Since you can practice wildly different languages with very different written alphabets and symbols,
you may not want to constantly be messing with your keyboard settings and remembering how to type specific
letters/characters/logograph/etc.

In Babilonia, you can have as many spellings as you want for any given word.
Simply separate them by slashes, and they will all be accepted. The correct spelling will still be shown 
to you afterwards (if you specified so), so you don't forget the _real_ spelling :wink:

For example, if you have :

- "être pressé / etre presse" -> both "être pressé" and "etre presse" will be accepted
- "mañana / manana" -> both "mañana" and "manana" will be accepted
- "苹果 / pín guǒ / pin guo" -> "苹果", "pín guǒ" and "pin guo" will all be accepted
- and so on...

### Optional elements

You can also add optional elements around words by putting them in parentheses.

For example, if you have
- "(to) understand" -> both "to understand" and "understand" will be valid
- "(der) Apfel" -> both "der Apfel" and "Apfel" will be valid
- and so on...

This feature is very useful to provide context too, for example play (guitar) as opposed to play (soccer)!

## Picking which languages to practice

Even though you *could* practice tons of languages at the same time, it doesn't mean you always *have* to!
Maybe you'd  like to focus on some languages.

You can choose which languages you would like to study in Languages -> Set Practice Languages.

Words whose languages are set to "practice" will be hidden when practicing,
and others will show. Also, ideas which do not have any words with languages set to "practice" will
not show up when practicing.

A very important feature of Babilonia is its flexibility when it comes to practicing different sets of languages.

## The goal

How you dashboard might look one day! :grinning:

![Example 3](images/dashboard-example.png)

## Database

The database is stored in the database.db file. You can make backups of that file and at
any point in the future, overwrite it with any other version and it will be restored.

Upon installation, you get an empty database, which you can then fill with ideas and words
that matter most to you.

As of now, there are no pre-defined databases to download, because there are as many different 
ways of using Babilonia as there are users.

However, it will be considered in future releases to have starter databases, or even allow users 
to share theirs!

## I'm convinced, how do I install this thing?

### Prerequisites

- Maven
- JDK 11

### Installing

    git clone https://github.com/emilepharand/Babilonia
    cd Babilonia
    mvn package
    cp target/Babilonia.jar database.db /your/install/path/

Then, to run it :

    java -jar /your/install/path/Babilonia.jar

## License

This project is licensed under the terms of the MIT license.

## Can I contribute?

Of course! Feel free to open an issue or make a pull request.

## Any questions?

Do not hesitate to contact me at emile.pharand.github@gmail.com :smile:
<br>
<br>
<br>
<sub>People often say that motivation doesn't last. Well, neither does bathing — that's why we recommend it daily. - Zig Ziglar</sub>