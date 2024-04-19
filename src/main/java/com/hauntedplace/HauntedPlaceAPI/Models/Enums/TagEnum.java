package com.hauntedplace.HauntedPlaceAPI.Models.Enums;
import com.hauntedplace.HauntedPlaceAPI.Entitys.Tag;
import jakarta.validation.constraints.NotNull;

public enum TagEnum {
    MOVIES("MOVIES"),
    MUSIC("MUSIC"),
    STYLE("STYLE"),
    LITERATURE("LITERATURE");

    private String value;

    TagEnum(String value) {
       this.value = value;
    }
    TagEnum(@NotNull Tag tag){
          switch (tag.getDescription())
          {
            case "MOVIES": this.value = "MOVIES"; break;
            case "MUSIC": this.value = "MUSIC"; break;
            case "STYLE": this.value = "STYLE"; break;
            case "LITERATURE": this.value = "LITERATURE";
          }

    }
    @Override
    public String toString() {
        return value;
    }
}
