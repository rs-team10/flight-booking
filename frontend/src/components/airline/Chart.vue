<script>
import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
    extends: Bar,
    mixins: [reactiveProp],
    

    data(){
        return {
            gradient: null,
            datacollection: {
				labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
				datasets: [
					{
                        label: 'Data One',
						backgroundColor: this.gradient,
						pointBackgroundColor: 'white',
						borderWidth: 1,
						pointBorderColor: '#249EBF',
						data: [40, 20, 30, 50, 90, 10, 20, 40, 50, 70, 90, 100]
					}
                ]
            },
            
            options: {
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							display: false
						}
					}],
					xAxes: [{
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							display: false
						}
					}]
				},
				legend: {
					display: false
				},
				tooltips: {
					enabled: true,
					mode: 'single',
					callbacks: {
						label: function(tooltipItems, data) {
							return tooltipItems.yLabel + ' tickets';
						}
					}
				},
				responsive: true,
				maintainAspectRatio: false,
				height: 200
            }   
		}
    },
    mounted () {
        this.renderChart(this.chartData, this.options)
    }
}
</script>
