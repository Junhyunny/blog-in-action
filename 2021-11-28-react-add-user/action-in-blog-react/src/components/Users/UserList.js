
import Card from "../UI/Card";
import clasess from './UserList.module.css';

const UserList = (props) => {

    if (props.users.length === 0) {
        return (
            <Card className={clasess.users}>
                <ul>
                    <li>
                        No Users.
                    </li>
                </ul>
            </Card>
        );
    }

    return (
        <Card className={clasess.users}>
            <ul>
                {props.users.map((user) =>
                    <li key={user.id}>
                        {user.name} ({user.age} years old)
                    </li>
                )}
            </ul>
        </Card>
    );
}

export default UserList;