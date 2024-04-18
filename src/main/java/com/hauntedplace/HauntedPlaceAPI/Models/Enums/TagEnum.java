package com.hauntedplace.HauntedPlaceAPI.Models.Enums;

import com.hauntedplace.HauntedPlaceAPI.Entitys.Tag;

public enum TagEnum {
        MOVIES("MOVIES"),
        MUSIC("MUSIC"),
        STYLE("STYLE"),
        LITERATURE("LITERATURE");

        private String value;

        TagEnum(String value) {
           this.value = value;
        }
        TagEnum(Tag tag){
              switch (tag.getDescription())
              {
                case "MOVIES": this.value = "MOVIES";
                case "MUSIC": this.value = "MUSIC";
                case "STYLE": this.value = "STYLE";
                case "LITERATURE": this.value = "LITERATURE";
              }

        }
}
