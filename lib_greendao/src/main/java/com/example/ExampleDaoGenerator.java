package com.example;/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * <p/>
 * Run it as a Java application (not Android).
 *
 * @author Markus
 */
public class ExampleDaoGenerator {

    private final static int verison = 1;
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(verison, "com.example");
        addConfig(schema);
        addParent(schema);
        new DaoGenerator().generateAll(schema, "D:\\tengbin\\project\\Ooooim_android\\lib_greendao\\src\\main\\java\\dao");
    }

    private static void addConfig(Schema schema) {
        Entity note = schema.addEntity("Config");
        note.addIdProperty();
        note.addStringProperty("key").notNull();
        note.addStringProperty("value").notNull();
    }

    //用户
    private static void addParent(Schema schema) {
        Entity note = schema.addEntity("Parent");
        note.addIdProperty();
        note.addStringProperty("uid");
        note.addStringProperty("token");
        note.addStringProperty("phone");
        note.addStringProperty("alias");
        note.addIntProperty("sex");
        note.addLongProperty("birthday");
        note.addStringProperty("headThumb");
    }


}