// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract VotingContract {

    struct Candidate {
        uint256 id;
        string name;
        uint256 voteCount;
    }

    struct Voting {
        uint256 id;
        string name;
        uint256 candidatesCount;
        uint256 votersCount;
    }

    uint256 private votingCount;

    // Мапінги для зберігання голосувань, кандидатів та виборців
    mapping(uint256 => Voting) private votings; // Зберігання голосувань
    mapping(uint256 => mapping(uint256 => Candidate)) private votingCandidates; // Зберігання кандидатів для кожного голосування (votingId => (candidateId => Candidate))
    mapping(uint256 => mapping(uint256 => bool)) private hasVoted; // Зберігання статусу голосування користувача (votingId => (userId => bool))
    mapping(uint256 => uint256[]) private votingVoters; // Зберігання списку userIds, які проголосували (votingId => list of userIds)

    // Події для сповіщення про створення нового голосування та голосування користувача
    event VotingCreated(uint256 votingId, string name);
    event Voted(uint256 votingId, uint256 candidateId, uint256 userId);

    // Функція для створення нового голосування з переліком кандидатів
    function createVoting(string memory name, string[] memory candidateNames) public {
        votingCount++;
        Voting storage newVoting = votings[votingCount];
        newVoting.id = votingCount;
        newVoting.name = name;
        newVoting.candidatesCount = candidateNames.length;
        newVoting.votersCount = 0;

        for (uint256 i = 0; i < candidateNames.length; i++) {
            votingCandidates[votingCount][i] = Candidate({
                id: i,
                name: candidateNames[i],
                voteCount: 0
            });
        }

        emit VotingCreated(votingCount, name);
    }

    // Функція для отримання інформації про конкретне голосування
    function getVotingById(uint256 votingId) public view returns (Voting memory) {
        return votings[votingId];
    }

    // Функція для отримання всіх голосувань
    function getAllVotings() public view returns (Voting[] memory) {
        Voting[] memory allVotings = new Voting[](votingCount);
        for (uint256 i = 1; i <= votingCount; i++) {
            allVotings[i - 1] = votings[i];
        }
        return allVotings;
    }

    // Функція для отримання інформації про конкретного кандидата у певному голосуванні
    function getCandidateById(uint256 votingId, uint256 candidateId) public view returns (Candidate memory) {
        return votingCandidates[votingId][candidateId];
    }

    // Функція для отримання всіх кандидатів для певного голосування
    function getAllCandidates(uint256 votingId) public view returns (Candidate[] memory) {
        uint256 candidatesCount = votings[votingId].candidatesCount;
        Candidate[] memory candidates = new Candidate[](candidatesCount);
        for (uint256 i = 0; i < candidatesCount; i++) {
            candidates[i] = votingCandidates[votingId][i];
        }
        return candidates;
    }

    // Функція для перевірки, чи користувач вже голосував у конкретному голосуванні
    function hasUserVoted(uint256 votingId, uint256 userId) public view returns (bool) {
        return hasVoted[votingId][userId];
    }

    // Функція для голосування за кандидата у конкретному голосуванні
    function vote(uint256 votingId, uint256 candidateId, uint256 userId) public {
        require(!hasVoted[votingId][userId], "You have already voted in this voting.");
        require(candidateId < votings[votingId].candidatesCount, "Invalid candidate.");

        votingCandidates[votingId][candidateId].voteCount++;
        hasVoted[votingId][userId] = true;
        votings[votingId].votersCount++;
        votingVoters[votingId].push(userId); // Додаємо userId до списку виборців

        emit Voted(votingId, candidateId, userId);
    }

    // Функція для отримання всіх виборців для певного голосування
    function getVotersByVotingId(uint256 votingId) public view returns (uint256[] memory) {
        return votingVoters[votingId];
    }
}
