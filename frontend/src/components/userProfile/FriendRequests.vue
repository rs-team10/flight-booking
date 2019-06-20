<template>
    <v-layout column ma-4>
        <v-data-table
            :headers="headers"
            :items="users"
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
                    <v-btn dark depressed color="green" @click="acceptFriendRequest(props.item.email)">
                        <span>Accept</span>
                    </v-btn>
                    <v-btn dark depressed color="red" @click="declineFriendRequest(props.item.email)">
                        <span>Decline</span>
                    </v-btn>
                </td>
            </template>
            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                No results.
            </v-alert>
        </v-data-table>
    </v-layout>
</template>

<script>
export default {
    name: 'friend-requests',
    data() {
        return {
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
                    align: 'left',
                    sortable: true,
                    value: 'status',
                    width: 130
                },
                {
                    text: "",
                    align: 'right',
                    sortable: false,
                    value: 'actions',
                    width: 300
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
        acceptFriendRequest: function(email) {
            var removeIndex = this.users.map(function(item) { return item.email; }).indexOf(email);

            var yourConfig = {
                headers: {
                    'Authorization': "Bearer " + localStorage.getItem("token"),
                    'Content-type': "text/plain"
                }
            };
            
            this.$axios.put('/api/acceptFriendRequest/', email, yourConfig).then((response) => {
                (removeIndex >= 0) && this.users.splice(removeIndex, 1);
                this.$swal('Accepted', 'Friend request accepted.', 'success');
            })
            .catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });           
        },
        declineFriendRequest: function(email) {
            var removeIndex = this.users.map(function(item) { return item.email; }).indexOf(email);
            
            var yourConfig = {
                headers: {
                    'Authorization': "Bearer " + localStorage.getItem("token"),
                    'Content-type': "text/plain"
                }
            };
            
            this.$axios.put('/api/declineFriendRequest/', email, yourConfig).then((response) => {
                (removeIndex >= 0) && this.users.splice(removeIndex, 1);
                this.$swal('Declined', 'Friend request declined.', 'success');
            })
            .catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
        }
    },
    created() {

        var yourConfig = {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token")
            }
        }

        this.$axios.get('/api/getAllFriendshipRequests/', yourConfig).then((response) => {
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