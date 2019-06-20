<template>
  <div class="users">
    <v-container class="my-1">
        <h1 class="mx-3">Search users</h1>
        <h4 class="mx-3">Type in the first or last name of the user you want to add to your friends list.</h4>
        <h4 class="mx-3">Note that the results do not contain users that are in your friends list.</h4>
        <v-layout row>
            <v-text-field
                v-model="search"
                append-icon="search"
                label="Search users"
                single-line
                class="mx-3"
                @keyup.enter.native="searchUsers"
            ></v-text-field>
            <v-btn @click="searchUsers" :disabled=isDisabled>
                <span>Search</span>
            </v-btn>
        </v-layout>

        <v-layout row wrap>
            <v-flex xs12 sm6 lg2 v-for="user in displayedUsers" :key="user.email" >
                <v-card flat class="text-xs-center ma-3" >
                    <v-responsive class="pt-4">
                        <v-avatar size="100" class="grey lighten-2">
                            <img src="@/assets/img/user.png">
                        </v-avatar>
                    </v-responsive>
                    <v-card-text>
                        <div class="subheading">{{ user.firstName + ' ' + user.lastName}}</div>
                        <div class="grey--text">{{ user.email }}</div>
                    </v-card-text>
                    <v-card-actions class="justify-center">
                        <v-btn flat color="primary" @click="sendFriendRequest(user.email)">
                            <v-flex>
                                <v-icon small left>add</v-icon>
                                <span>Add to Friends</span>
                            </v-flex>
                        </v-btn>
                        </v-card-actions>
                </v-card>
            </v-flex>
        </v-layout>
          <div class="text-xs-center">
            <v-pagination
            v-model="page"
            :length="pages.length"
            ></v-pagination>
            <h2  v-if="resultsListEmpty">No results</h2>
        </div>
    </v-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
        search: '',
        users: [],
        page: 1,
        perPage: 12,
        pages: []
    }
  },
  methods: {
    searchUsers () {

        if (/\S/.test(this.search)) {
        
            var yourConfig = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            };

            this.$axios.get('/api/searchUsers/' + this.search, yourConfig).then((response) => {
                this.users = response.data.users;
                if(this.users.length == 0) {
                    this.$swal("No results", "Your search returned 0 results. Please try another query.", 'info');
                }
            }).catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
        }
    },
    setPages () {
        this.pages = [];
        let numberOfPages = Math.ceil(this.users.length / this.perPage);
        for (let index = 1; index <= numberOfPages; index++) {
            this.pages.push(index);
        }
    },
    paginate (users) {
        let page = this.page;
        let perPage = this.perPage;
        let from = (page * perPage) - perPage;
        let to = (page * perPage);
        return users.slice(from, to);
    },
    sendFriendRequest(email) {

        var yourConfig = {
            headers: {
                'Authorization': "Bearer " + localStorage.getItem("token"),
                'Content-type': "text/plain"
            }
        };

        this.$axios.put('/api/addFriend/', email, yourConfig).then((response) => {
            this.$swal('Success', 'Friend request sent successfuly', 'success');
        }).catch(response => {
              this.$swal('Unable to complete action', 'The user is already in your friends list', 'info');
        });
    }
  },
  watch: {
    users () {
      this.setPages();
    }
  }, 
  computed: {
    displayedUsers () {
      return this.paginate(this.users);
    },
    isDisabled() {
        return this.search.length == 0;
    },
    resultsListEmpty() {
        return this.users.length == 0;
    }
  },
}
</script>