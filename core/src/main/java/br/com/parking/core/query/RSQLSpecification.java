package br.com.parking.core.query;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class RSQLSpecification<T> implements Specification<T> {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private String property;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			ComparisonOperator		operator;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private List<String> arguments;

	public RSQLSpecification(String property, ComparisonOperator operator, List<String> arguments) {
		setProperty(property);
		setOperator(operator);
		setArguments(arguments);
	}

	@SuppressWarnings("DuplicateExpressions")
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<?> arguments		= castArguments(root);
		Object argument 		= arguments.get(0);

		switch (RSQLSearchOperation.getSimpleOperator(operator)) {
			case EQUAL: {
				if (argument instanceof String) {
					return builder.like(builder.upper(navigate(root)), argument.toString().replace('*', '%').toUpperCase());
				} else if (argument == null) {
					return builder.isNull(navigate(root));
				} else {
					return builder.equal(navigate(root), argument);
				}
			}

			case NOT_EQUAL: {
				if (argument instanceof String) {
					return builder.notLike(builder.upper(navigate(root)), argument.toString().replace('*', '%').toUpperCase());
				} else if (argument == null) {
					return builder.isNotNull(navigate(root));
				} else {
					return builder.notEqual(navigate(root), argument);
				}
			}

			case GREATER_THAN: {
				return builder.greaterThan(navigate(root), argument.toString());
			}

			case GREATER_THAN_OR_EQUAL: {
				return builder.greaterThanOrEqualTo(navigate(root), argument.toString());
			}

			case LESS_THAN: {
				return builder.lessThan(navigate(root), argument.toString());
			}

			case LESS_THAN_OR_EQUAL: {
				return builder.lessThanOrEqualTo(navigate(root), argument.toString());
			}

			case IN:
				return navigate(root).in(arguments);

			case NOT_IN:
				return builder.not(navigate(root).in(arguments));
		}

		return null;
	}

	private List<?> castArguments(final Root<T> root) {
		Class<?> type			= navigate(root).getJavaType();

		return getArguments().stream().map(arg -> {
			if (type.equals(int.class) || type.equals(Integer.class)) {
				return Integer.parseInt(arg);
			} else if (type.equals(Long.class) || type.equals(long.class)) {
				return Long.parseLong(arg);
			} else if (type.equals(Double.class) || type.equals(double.class)) {
				return Double.parseDouble(arg);
			} else if (type.equals(Short.class) || type.equals(short.class)) {
				return Short.parseShort(arg);
			} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
				return Boolean.parseBoolean(arg);
			} else if (Enum.class.isAssignableFrom(type)) {
				try {
					return type.getDeclaredMethod("valueOf", String.class).invoke(type, arg);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					return null;
				}
			} else {
				return arg;
			}
		}).collect(Collectors.toList());
	}

	private Path<String> navigate(final Root<T> root) {
		String[]			properties		= getProperty().split("\\.");
		Path<String> 		leaf			= root.get(properties[0]);

		for (int i = 1; i < properties.length - 2; i++) {
			leaf = root.join(properties[i]);
		}

		if (properties.length == 1) {
			return leaf;
		}

		return leaf.get(properties[properties.length - 1]);
	}
}
