package net.elytrapvp.ffa.inventories;

import net.elytrapvp.elytrapvp.chat.ChatUtils;
import net.elytrapvp.elytrapvp.gui.CustomGUI;
import net.elytrapvp.elytrapvp.items.ItemBuilder;
import net.elytrapvp.elytrapvp.items.SkullBuilder;
import net.elytrapvp.ffa.enums.TagType;
import net.elytrapvp.ffa.objects.CustomPlayer;
import net.elytrapvp.ffa.objects.Tag;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TagsGUI extends CustomGUI {
    private static final int[] slots = new int[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34};
    public TagsGUI(Player player, int page, TagType type) {
        super(54, "Tags - Page " + page);

        int s = 0;
        for(int i = ((page - 1) * 21) + 1; i <= type.getTags().size(); i++) {
            if(i > (page * slots.length) || s > slots.length - 1) {
                break;
            }

            Tag t = type.getTags().get(i - 1);
            setItem(slots[s], t.getIcon(player), (p, a) -> t.unlock(player));
            s++;
        }

        fillers();

        if(page == 1) {
            setItem(38, new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6").setDisplayName("&cNo more pages!").build());
        }
        else {
            setItem(38, new SkullBuilder("754aaab39764641ff8627932d2fe1a4ccced96537508d4abc6cd5fbbb87913").setDisplayName("&aPage " + (page - 1)).build(), (p,a) -> {
                new TagsGUI(player, page - 1, type).open(player);
            });
        }

        if(type.getTags().size() > (page * 21)) {
            setItem(42, new SkullBuilder("61d0f82a2a4cdd85f79f4d9d9798f9c3a5bccbe9c7f2e27c5fc836651a8f3f45").setDisplayName("&aPage " + (page + 1)).build(), (p,a) -> {
                new TagsGUI(player, page + 1, type).open(player);
            });
        }
        else {
            setItem(42, new SkullBuilder("abae89e92ac362635ba3e9fb7c12b7ddd9b38adb11df8aa1aff3e51ac428a4").setDisplayName("&cNo more pages!").build());
        }

        setItem(0, new SkullBuilder("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6").setDisplayName("&cBack").build(), (p,a) -> {
            new CosmeticsGUI().open(player);
        });

        setItem(40, new ItemBuilder(Material.BARRIER).setDisplayName("&cReset").build(), (p, a ) -> {
            CustomPlayer ep = CustomPlayer.getCustomPlayers().get(p.getUniqueId());
            ep.setTag(0);
            p.getInventory().clear(39);
            p.closeInventory();
            ChatUtils.chat(p, "&a&lCosmetics &8» &aTag has been reset.");
        });
    }

    private void fillers() {
        List<Integer> slots = Arrays.asList(1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53);
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build();
        slots.forEach(i -> setItem(i, filler));
    }
}