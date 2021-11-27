
import ChartBar from "./ChartBar";
import './Chart.css';

const Chart = (props) => {

    const dataPointValues = props.dataPoints.map(dataPoint => dataPoint.value);
    const totalMaximum = Math.max(...dataPointValues);

    return (
        <div className="chart">
            {props.dataPoints.map((item) => 
                <ChartBar 
                    key={item.label}
                    value={item.value} 
                    maxValue={totalMaximum}
                    label={item.label} 
                />)
            }
        </div>
    );
};

export default Chart;