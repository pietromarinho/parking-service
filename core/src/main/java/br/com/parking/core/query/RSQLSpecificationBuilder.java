package br.com.parking.core.query;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RSQLSpecificationBuilder<T> {
	public Specification<T> createSpecification(Node node) {
		if (node instanceof LogicalNode) {
			return createSpecification((LogicalNode) node);
		}
		if (node instanceof ComparisonNode) {
			return createSpecification((ComparisonNode) node);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public Specification<T> createSpecification(LogicalNode logicalNode) {
		List<Specification> specifications			= logicalNode.getChildren().stream().map(this::createSpecification).filter(Objects::nonNull).collect(Collectors.toList());
		Specification result					= specifications.get(0);

		if (logicalNode.getOperator() == LogicalOperator.AND) {
			for (int i = 1; i < specifications.size(); i++) {
				result		= Specification.where(result).and(specifications.get(i));
			}
		} else if (logicalNode.getOperator() == LogicalOperator.OR) {
			for (int i = 1; i < specifications.size(); i++) {
				result		= Specification.where(result).or(specifications.get(i));
			}
		}

		return result;
	}

	public Specification<T> createSpecification(ComparisonNode comparisonNode) {
		return Specification.where(new RSQLSpecification<>(comparisonNode.getSelector(), comparisonNode.getOperator(), comparisonNode.getArguments()));
	}
}
