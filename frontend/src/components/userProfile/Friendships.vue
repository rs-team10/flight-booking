<template>
    <v-layout column ma-4>
        <v-text-field
            v-model="search"
            append-icon="search"
            label="Search friends list"
            single-line
        ></v-text-field>

        <v-data-table
            :headers="headers"
            :items="users"
            :search="search"
            v-bind:pagination.sync="pagination">
            <template v-slot:items="props">
                <td>{{ props.item.firstName }}</td>
                <td>{{ props.item.lastName }}</td>
                <td>{{ props.item.email }}</td>
                <td class="text-xs-right disable-events">              
                    <v-btn depressed :color="computeButtonColor(props.item.status)">
                        <span>{{ props.item.status }}</span>
                    </v-btn>
                </td>
                <td class="text-xs-right">              
                    <v-btn dark depressed color="red" @click="deleteFriendRequest(props.item.email)">
                        <span>Remove</span>
                    </v-btn>
                </td>
            </template>
            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                Your search for "{{ search }}" found no results.
            </v-alert>
        </v-data-table>
    </v-layout>
</template>

<script>
export default {
    name: "friendships",
    data() {
        return {
            search: '',
            headers: [
                {
                    text: "First Name",
                    align: 'left',
                    sortable: true,
                    value: 'firstName'
                },
                {
                    text: "Last Name",
                    align: 'left',
                    sortable: true,
                    value: 'lastName'
                },
                {
                    text: "Email",
                    align: 'left',
                    sortable: true,
                    value: 'email'
                },
                {
                    text: "Status",
                    align: 'center',
                    sortable: true,
                    value: 'status',
                    width: 170
                },
                {
                    text: "Actions",
                    align: 'center',
                    sortable: false,
                    value: 'actions',
                    width: 70
                }
            ],
            pagination: {'sortBy': 'status', 'descending': false, 'rowsPerPage': 5},
            users: []
        };
    },
    methods: {
        computeButtonColor(status) {
            if(status.toLowerCase() == "waiting")
                return "yellow";
            else if (status.toLowerCase() == "accepted")
                return "green";
            else
                return "red";
        },
        deleteFriendRequest: function(email) {

            this.$swal({
                title: 'Are you sure?',
                text: 'You can\'t revert your action',
                type: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                showCloseButton: true,
            }).then((result) => {

                if(result.value) {

                    var removeIndex = this.users.map(function(item) { return item.email; }).indexOf(email);
                    (removeIndex >= 0) && this.users.splice(removeIndex, 1);

                    var yourConfig = {
                        headers: {
                            'Authorization': "Bearer " + localStorage.getItem("token"),
                            'Content-type': "text/plain"
                        }
                    };

                    this.$axios.put('/api/removeFriend/', email, yourConfig).then((response) => {
                        this.$swal('Deleted', 'You successfully removed the user from your friends list', 'success');
                    })
                    .catch((error) => {
                        this.$swal("Error", error.response.data.message, 'error');
                    });           
                }
            });
        }
    },
    created() {

        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        }

        this.$axios.get('/api/getAllFriends/', yourConfig).then((response) => {
            this.users = response.data;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
}
</script>

<style>
.disable-events {
  pointer-events: none
}
</style>
