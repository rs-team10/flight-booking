<template>
  <div class="users">
    <v-container class="my-1">
        <h1 class="mx-3">Friendships</h1>
        <v-text-field
            v-model="search"
            append-icon="search"
            label="Search friends list"
            single-line
            class="mx-3"
        ></v-text-field>
        <v-layout row wrap>
            <v-flex xs12 sm6 md4 lg2 v-for="user in users" :key="user.email" >
                <v-card flat class="text-xs-center ma-3" >
                    <v-responsive class="pt-4">
                        <v-avatar size="100" class="grey lighten-2">
                            <img :src="user.avatar">
                        </v-avatar>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{ user.firstName + ' ' + user.lastName}}</div>
                        <div class="grey--text">{{ user.email }}</div>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn flat color="primary">
                            <v-flex>
                                <v-icon small left>add</v-icon>
                                <span>Add to Friends</span>
                            </v-flex>
                        </v-btn>
                        </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
        search: '',
        users: [
            { firstName: 'FirstName', lastName: 'LastName', email: 'test1@flightbooking.com', avatar: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9RQEmYAlNKd4SYoZKw_UYBqH82jvxtZmo0mBIzNZrgA4Ycn5A' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test2@flightbooking.com', avatar: 'https://cdn.iconscout.com/icon/free/png-256/avatar-373-456325.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test3@flightbooking.com', avatar: 'https://cdn.iconscout.com/icon/free/png-256/avatar-372-456324.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test4@flightbooking.com', avatar: 'https://png.pngtree.com/svg/20170602/0db185fb9c.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test5@flightbooking.com', avatar: 'https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/10_avatar-512.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test6@flightbooking.com', avatar: 'https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/3_avatar-512.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test7@flightbooking.com', avatar: 'https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/11_avatar-512.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test8@flightbooking.com', avatar: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8ZKRHEVYc1Mnok2Vsko87zHvfgWGgL5p9mrissmMKdYyOtlpc' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test9@flightbooking.com', avatar: 'https://tse3.mm.bing.net/th?id=OIP.r3ObWg-koGXAfevKgtwNDwHaIh&w=190&h=219&c=8&o=5&pid=1.7' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test10@flightbooking.com', avatar: 'https://cdn.iconscout.com/icon/free/png-256/avatar-367-456319.png' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test11@flightbooking.com', avatar: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXf3fe0_GI60xYhaz4dn-aNUI5lugp3HXKSSPJjB4uaV0KON98dg' },
            { firstName: 'FirstName', lastName: 'LastName', email: 'test12@flightbooking.com', avatar: 'https://cdn3.iconfinder.com/data/icons/avatars-9/145/Avatar_Penguin-512.png' }
        ]
    }
  }
}
</script>