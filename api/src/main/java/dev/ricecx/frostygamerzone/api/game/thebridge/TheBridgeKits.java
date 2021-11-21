package dev.ricecx.frostygamerzone.api.game.thebridge;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TheBridgeKits {
    WARRIOR("Warrior", "Start as a young warrior, ready to clash and defend the nexus, at all costs!"),
    ARCHER("Archer", "Aim high, shoot hard! Be a defense against enemies using ranged weapons, and a little bit of skill."),
    ALCHEMIST("Alchemist", "Be a supporter, or a potter, up to you! Brew faster with the help of the passive skill, as an experienced alchemist."),
    BESERKER("Beserker", "With the power of brute strength, creating blasts has never been more easy."),
    BLACKSMITH("Blacksmith", "With years of experience, passion, and hard work, ingots are a blacksmith's best friend."),
    BUILDER("Builder", "Show us your building skills, in a defensive or aggressive way."),
    BUTCHER("Butcher", "As a skillful chopper, use the experience of butchering bones to chopping enemies"),
    ENCHANTER("Enchanter", "A knowledgeable master from nature, use your skills to earn natural mana efficiently"),
    HEALER("Healer", "Humble, truthful and always cares for teammates. Learn to get extra health from enemies, as well as heal surrounding allies."),
    HERO("Hero", "You have been chosen as a knight by the king, holding a blessed sword of sharpness, believed to make people fly"),
    MEDIC("Medic", "Support your allies by healing them with the power of nature, only skilled herbalists are able to become a medic"),
    MINER("Miner", "After months and months of work, you've learned how to efficiently break rocks, ores, and become durable with strength"),
    NEWB("Newb", "Failure is never an option! The gods have blessed those who keep trying with the one and only Newb Sword."),
    POISONMAN("Poisonman", "Being kidnapped by witches, they forced you to learn poison and how it works. You've turned your back on them, and now, you're a poison master"),
    PYRO("Pyro", "Broke out of the fire chambers, lava, and hell. Burn people, like how the devil has burned you"),
    SPRINTER("Sprinter", "Super athlete, reincarnated as a speedy warrior. Sometimes, speed is more important than strength"),
    SPY("Spy", "Abilities only learned by the family of assassins, in a far land. Invisibility is your secret weapon"),
    STOPPER("Stopper", "You're a drukn manaic with a little bit of alchemy. Hitting enemies unconscious has never been more easy."),
    SWAPPER("Swapper", "A little sneaky with the help of the Alchemist. Switch places in enemies and with a little play, you can even counter the hardest enemies!"),
    TANK("Tank", "A blacksmith's favorite hobby. Hard and strong, your armor can never break from a sword"),
    TELEPORTER("Teleporter", "Blessed by the Enderman King, you can now teleport to enemies using your special rod"),
    TOOLSMAN("Toolsmen", "A blacksmith's customer can only become so strong, with the help of tools made with passion and love!"),
    TRAITOR("Traitor", "Sometimes, trickery is the best option to play your enemies"),
    TRAPPER("Trapper", "As the main defender of the kingdom, use your medieval skills to blast enemies off their foot"),
    VAMPIRE("Vampire", "Enemy blood is your favorite food, and milk is the thing you hate the most"),
    ;

    private final String name;
    private final String description;
}
